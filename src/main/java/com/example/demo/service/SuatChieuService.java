//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Map;
//
//// service/SuatChieuService.java
//@Service
//public class SuatChieuService {
//
//    private final SuatChieuRepository suatChieuRepository;
//
//    public SuatChieuService(SuatChieuRepository suatChieuRepository) {
//        this.suatChieuRepository = suatChieuRepository;
//    }
//
//    // Lấy phim theo ngày + rạp
//    public List<Map<String, Object>> getPhimTheoNgayVaRap(
//            String maRap, String ngay) {
//        LocalDate localDate = LocalDate.parse(ngay);
//        return suatChieuRepository.timPhimTheoNgayVaRap(maRap, localDate);
//    }
//
//    // Lấy suất chiếu theo phim + ngày + rạp
//    public List<Map<String, Object>> getSuatChieuConHan(
//            String maPhim, String maRap, String ngay) {
//        LocalDate localDate = LocalDate.parse(ngay);
//        return suatChieuRepository.timSuatChieuConHan(maPhim, maRap, localDate);
//    }
//}