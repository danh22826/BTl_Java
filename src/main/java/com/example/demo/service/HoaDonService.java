package com.example.demo.service;

import com.example.demo.constant.HoaDonStatus;
import com.example.demo.constant.VeStatus;
import com.example.demo.dto.request.HoaDon.CreateHoaDonRequest;
import com.example.demo.dto.response.HoaDonDetailResponse;
import com.example.demo.dto.response.HoaDonVeResponse;
import com.example.demo.entity.Ghe;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.SuatChieu;
import com.example.demo.entity.Ve;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ConflictException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.GheRepository;
import com.example.demo.repository.HoaDonRepository;
import com.example.demo.repository.KhachHangRepository;
import com.example.demo.repository.SuatChieuRepository;
import com.example.demo.repository.VeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class HoaDonService {

    private static final int HOLD_MINUTES = 10;
    private static final EnumSet<VeStatus> ACTIVE_VE_STATUSES = EnumSet.of(VeStatus.DA_DAT, VeStatus.DA_THANH_TOAN);

    private final HoaDonRepository hoaDonRepository;
    private final KhachHangRepository khachHangRepository;
    private final SuatChieuRepository suatChieuRepository;
    private final GheRepository gheRepository;
    private final VeRepository veRepository;

    public HoaDonService(HoaDonRepository hoaDonRepository,
                         KhachHangRepository khachHangRepository,
                         SuatChieuRepository suatChieuRepository,
                         GheRepository gheRepository,
                         VeRepository veRepository) {
        this.hoaDonRepository = hoaDonRepository;
        this.khachHangRepository = khachHangRepository;
        this.suatChieuRepository = suatChieuRepository;
        this.gheRepository = gheRepository;
        this.veRepository = veRepository;
    }

    @Transactional
    public HoaDonDetailResponse createPendingInvoice(CreateHoaDonRequest request) {
        String maSuat = normalize(request.getMaSuat());
        String maKhachHang = normalize(request.getMaKhachHang());
        Set<String> dsGhe = sanitizeSeatIds(request.getDsGhe());

        if (maKhachHang.isBlank()) {
            throw new BadRequestException("Mã khách hàng không được để trống");
        }

        if (!khachHangRepository.existsById(maKhachHang)) {
            throw new BadRequestException("Không tìm thấy khách hàng tương ứng với mã đăng nhập hiện tại");
        }

        if (dsGhe.size() > 8) {
            throw new BadRequestException("Tối đa 8 ghế cho mỗi hóa đơn");
        }

        releaseExpiredPendingInvoices(maSuat);

        SuatChieu suatChieu = suatChieuRepository.findById(maSuat)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy suất chiếu"));

        List<Ghe> ghes = gheRepository.findAllById(dsGhe)
                .stream()
                .sorted(Comparator.comparing(Ghe::getSoHang).thenComparing(Ghe::getSoCot))
                .toList();

        if (ghes.size() != dsGhe.size()) {
            throw new BadRequestException("Có ghế không tồn tại trong hệ thống");
        }

        String maPhong = suatChieu.getPhongChieu().getMaPhong();
        List<String> gheSaiPhong = ghes.stream()
                .filter(ghe -> !maPhong.equals(ghe.getPhongChieu().getMaPhong()))
                .map(Ghe::getMaGhe)
                .toList();
        if (!gheSaiPhong.isEmpty()) {
            throw new ConflictException("Ghế không thuộc phòng của suất chiếu: " + String.join(", ", gheSaiPhong));
        }

        List<Ve> veDaCo = veRepository.findBySuatChieu_MaSuatAndGhe_MaGheInAndTrangThaiVeIn(maSuat, dsGhe, ACTIVE_VE_STATUSES)
                .stream()
                .filter(this::isTicketActive)
                .toList();

        if (!veDaCo.isEmpty()) {
            String dsGheDaDat = veDaCo.stream()
                    .map(ve -> ve.getGhe().getTenGhe())
                    .sorted()
                    .collect(Collectors.joining(", "));
            throw new ConflictException("Ghế đã được giữ hoặc thanh toán: " + dsGheDaDat);
        }

        long tongGhePhong = gheRepository.countByPhongChieu_MaPhong(maPhong);
        long soVeDangHoatDong = veRepository.findBySuatChieu_MaSuatAndTrangThaiVeIn(maSuat, ACTIVE_VE_STATUSES)
                .stream()
                .filter(this::isTicketActive)
                .count();

        if (soVeDangHoatDong + dsGhe.size() > tongGhePhong) {
            throw new ConflictException("Số vé của suất chiếu vượt sức chứa phòng");
        }

        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaDon(generateId("HD", hoaDonRepository::existsById));

        // Cập nhật ánh xạ sang KhachHang Entity
        KhachHang khachHangEntity = khachHangRepository.findById(maKhachHang)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khách hàng"));
        hoaDon.setKhachHang(khachHangEntity);

        hoaDon.setThoiGianDat(LocalDateTime.now());
        hoaDon.setTrangThai(HoaDonStatus.CHUA_THANH_TOAN);
        hoaDon.setTongTien(calculateTotal(suatChieu, ghes));

        HoaDon savedHoaDon = hoaDonRepository.save(hoaDon);

        List<Ve> dsVe = new ArrayList<>();
        for (Ghe ghe : ghes) {
            Ve ve = new Ve();
            ve.setMaVe(generateId("VE", veRepository::existsById));
            ve.setHoaDon(savedHoaDon);
            ve.setSuatChieu(suatChieu);
            ve.setGhe(ghe);
            ve.setTrangThaiVe(VeStatus.DA_DAT);
            dsVe.add(ve);
        }

        // Bắt lỗi Race Condition (Xung đột khi 2 người đặt cùng 1 ghế ở cùng 1 thời điểm)
        try {
            veRepository.saveAll(dsVe);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Rất tiếc, ghế bạn chọn vừa có người nhanh tay đặt mất rồi! Vui lòng tải lại trang và chọn ghế khác.");
        }

        return toDetail(savedHoaDon, dsVe);
    }

    @Transactional
    public HoaDonDetailResponse getDetail(String maDon) {
        HoaDon hoaDon = hoaDonRepository.findById(maDon)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hóa đơn"));

        expireIfNeeded(hoaDon);
        List<Ve> dsVe = veRepository.findByHoaDon_MaDonOrderByGhe_SoHangAscGhe_SoCotAsc(maDon);
        return toDetail(hoaDon, dsVe);
    }

    @Transactional
    public HoaDonDetailResponse pay(String maDon, String phuongThucThanhToan) {
        HoaDon hoaDon = hoaDonRepository.findById(maDon)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hóa đơn"));

        expireIfNeeded(hoaDon);

        if (hoaDon.getTrangThai() == HoaDonStatus.DA_HUY) {
            throw new ConflictException("Hóa đơn đã bị hủy");
        }

        if (hoaDon.getTrangThai() == HoaDonStatus.DA_THANH_TOAN) {
            return getDetail(maDon);
        }

        hoaDon.setTrangThai(HoaDonStatus.DA_THANH_TOAN);
        hoaDon.setThoiGianThanhToan(LocalDateTime.now());
        hoaDon.setPhuongThucThanhToan(normalize(phuongThucThanhToan));
        hoaDonRepository.save(hoaDon);

        List<Ve> dsVe = veRepository.findByHoaDon_MaDonOrderByGhe_SoHangAscGhe_SoCotAsc(maDon);
        for (Ve ve : dsVe) {
            ve.setTrangThaiVe(VeStatus.DA_THANH_TOAN);
        }
        veRepository.saveAll(dsVe);

        return toDetail(hoaDon, dsVe);
    }

    @Transactional
    public HoaDonDetailResponse cancel(String maDon) {
        HoaDon hoaDon = hoaDonRepository.findById(maDon)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hóa đơn"));

        if (hoaDon.getTrangThai() == HoaDonStatus.DA_THANH_TOAN) {
            throw new ConflictException("Không thể hủy hóa đơn đã thanh toán");
        }

        List<Ve> dsVe = veRepository.findByHoaDon_MaDonOrderByGhe_SoHangAscGhe_SoCotAsc(maDon);
        hoaDon.setTrangThai(HoaDonStatus.DA_HUY);
        hoaDonRepository.save(hoaDon);
        veRepository.deleteByHoaDon_MaDon(maDon);

        return toDetail(hoaDon, dsVe);
    }

    protected void releaseExpiredPendingInvoices(String maSuat) {
        List<Ve> veHetHan = veRepository.findBySuatChieu_MaSuatAndHoaDon_TrangThaiAndHoaDon_ThoiGianDatBefore(
                maSuat,
                HoaDonStatus.CHUA_THANH_TOAN,
                LocalDateTime.now().minusMinutes(HOLD_MINUTES)
        );

        Set<String> maHoaDons = veHetHan.stream()
                .map(ve -> ve.getHoaDon().getMaDon())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        for (String maDon : maHoaDons) {
            HoaDon hoaDon = hoaDonRepository.findById(maDon).orElse(null);
            if (hoaDon == null || hoaDon.getTrangThai() != HoaDonStatus.CHUA_THANH_TOAN) {
                continue;
            }
            hoaDon.setTrangThai(HoaDonStatus.DA_HUY);
            hoaDonRepository.save(hoaDon);
            veRepository.deleteByHoaDon_MaDon(maDon);
        }
    }

    protected void expireIfNeeded(HoaDon hoaDon) {
        if (hoaDon.getTrangThai() != HoaDonStatus.CHUA_THANH_TOAN) {
            return;
        }

        LocalDateTime thoiGianDat = hoaDon.getThoiGianDat();
        if (thoiGianDat == null || thoiGianDat.plusMinutes(HOLD_MINUTES).isAfter(LocalDateTime.now())) {
            return;
        }

        hoaDon.setTrangThai(HoaDonStatus.DA_HUY);
        hoaDonRepository.save(hoaDon);
        veRepository.deleteByHoaDon_MaDon(hoaDon.getMaDon());
    }

    private boolean isTicketActive(Ve ve) {
        if (ve.getTrangThaiVe() == VeStatus.DA_THANH_TOAN) {
            return true;
        }

        if (ve.getTrangThaiVe() != VeStatus.DA_DAT || ve.getHoaDon() == null) {
            return false;
        }

        return ve.getHoaDon().getTrangThai() == HoaDonStatus.CHUA_THANH_TOAN
                && ve.getHoaDon().getThoiGianDat() != null
                && ve.getHoaDon().getThoiGianDat().plusMinutes(HOLD_MINUTES).isAfter(LocalDateTime.now());
    }

    private BigDecimal calculateTotal(SuatChieu suatChieu, Collection<Ghe> ghes) {
        BigDecimal basePrice = suatChieu.getGia() == null ? BigDecimal.ZERO : suatChieu.getGia();
        return ghes.stream()
                .map(ghe -> basePrice.add(ghe.getLoaiGhe().getGiaPhuThu() == null ? BigDecimal.ZERO : ghe.getLoaiGhe().getGiaPhuThu()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private HoaDonDetailResponse toDetail(HoaDon hoaDon, List<Ve> dsVe) {
        HoaDonDetailResponse response = new HoaDonDetailResponse();
        response.setMaDon(hoaDon.getMaDon());

        // Lấy mã khách hàng thông qua Entity
        response.setMaKhachHang(hoaDon.getKhachHang().getMaKhachHang());

        response.setTongTien(hoaDon.getTongTien());
        response.setThoiGianDat(hoaDon.getThoiGianDat());
        response.setThoiGianThanhToan(hoaDon.getThoiGianThanhToan());
        response.setHanThanhToan(hoaDon.getThoiGianDat() == null ? null : hoaDon.getThoiGianDat().plusMinutes(HOLD_MINUTES));
        response.setTrangThai(hoaDon.getTrangThai() == null ? null : hoaDon.getTrangThai().name());
        response.setPhuongThucThanhToan(hoaDon.getPhuongThucThanhToan());
        response.setSoVe(dsVe.size());
        response.setCoTheThanhToan(hoaDon.getTrangThai() == HoaDonStatus.CHUA_THANH_TOAN
                && response.getHanThanhToan() != null
                && response.getHanThanhToan().isAfter(LocalDateTime.now()));

        if (!dsVe.isEmpty()) {
            Ve veDauTien = dsVe.get(0);
            SuatChieu suatChieu = veDauTien.getSuatChieu();
            response.setMaSuat(suatChieu.getMaSuat());
            response.setNgayChieu(suatChieu.getNgayChieu());
            response.setGioChieu(suatChieu.getGioChieu());
            response.setGiaVeCoBan(suatChieu.getGia());
            response.setMaPhim(suatChieu.getPhim().getMaPhim());
            response.setTenPhim(suatChieu.getPhim().getTenPhim());
            response.setPoster(suatChieu.getPhim().getPoster());
            response.setMaPhong(suatChieu.getPhongChieu().getMaPhong());
            response.setTenPhong(suatChieu.getPhongChieu().getTenPhong());
            response.setTongGhe((int) gheRepository.countByPhongChieu_MaPhong(suatChieu.getPhongChieu().getMaPhong()));
            response.setMaRap(suatChieu.getPhongChieu().getRap().getMaRap());
            response.setTenRap(suatChieu.getPhongChieu().getRap().getTenRap());
        }

        response.setDsVe(dsVe.stream().map(this::toTicketResponse).toList());
        return response;
    }

    private HoaDonVeResponse toTicketResponse(Ve ve) {
        HoaDonVeResponse response = new HoaDonVeResponse();
        BigDecimal giaVeCoBan = ve.getSuatChieu().getGia() == null ? BigDecimal.ZERO : ve.getSuatChieu().getGia();
        BigDecimal phuThu = ve.getGhe().getLoaiGhe().getGiaPhuThu() == null ? BigDecimal.ZERO : ve.getGhe().getLoaiGhe().getGiaPhuThu();

        response.setMaVe(ve.getMaVe());
        response.setMaGhe(ve.getGhe().getMaGhe());
        response.setTenGhe(ve.getGhe().getTenGhe());
        response.setTenLoaiGhe(ve.getGhe().getLoaiGhe().getTenLoaiGhe());
        response.setGiaVeCoBan(giaVeCoBan);
        response.setPhuThu(phuThu);
        response.setThanhTien(giaVeCoBan.add(phuThu));
        response.setTrangThaiVe(ve.getTrangThaiVe().name());
        return response;
    }

    private Set<String> sanitizeSeatIds(List<String> dsGhe) {
        if (dsGhe == null || dsGhe.isEmpty()) {
            throw new BadRequestException("Danh sách ghế không được để trống");
        }

        return dsGhe.stream()
                .map(this::normalize)
                .filter(value -> !value.isBlank())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    private String generateId(String prefix, java.util.function.Predicate<String> exists) {
        for (int i = 0; i < 20; i++) {
            String id = prefix + UUID.randomUUID().toString()
                    .replace("-", "")
                    .substring(0, 8)
                    .toUpperCase();
            if (!exists.test(id)) {
                return id;
            }
        }
        throw new ConflictException("Không thể tạo mã mới, vui lòng thử lại");
    }
}