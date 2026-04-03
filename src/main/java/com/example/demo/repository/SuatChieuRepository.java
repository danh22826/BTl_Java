package com.example.demo.repository;

import com.example.demo.entity.SuatChieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface SuatChieuRepository extends JpaRepository<SuatChieu, String> {

    @Query(value = """
        SELECT DISTINCT
            p.MaPhim,
            p.TenPhim,
            p.MoTa,
            p.Poster,
            p.ThoiLuong,
            p.NgayKhoiChieu,
            p.DoTuoiPhuHop,
            p.NgonNgu
        FROM Phim p
        JOIN SuatChieu s ON p.MaPhim = s.MaPhim
        LEFT JOIN PhongChieu ph ON s.MaPhong = ph.MaPhong
        WHERE s.NgayChieu = :ngay
          AND (ph.MaRap = :maRap OR s.MaRap = :maRap)
        ORDER BY p.TenPhim
    """, nativeQuery = true)
    List<Map<String, Object>> timPhimTheoNgayVaRap(
            @Param("maRap") String maRap,
            @Param("ngay")  LocalDate ngay
    );

    @Query(value = """
        SELECT
            s.MaSuat,
            CONVERT(VARCHAR(5), s.GioChieu, 108)  AS GioChieu,
            s.Gia,
            ph.MaPhong,
            ph.TenPhong,
            lp.TenLoaiPhong,
            ph.SucChua                             AS TongGhe,
            (
                SELECT COUNT(*)
                FROM Ve v
                WHERE v.MaSuat = s.MaSuat
                  AND v.TrangThaiVe = 'TRONG'
            )                                      AS SoGheTrong
        FROM SuatChieu s
        LEFT JOIN PhongChieu ph ON s.MaPhong      = ph.MaPhong
        LEFT JOIN LoaiPhong  lp ON ph.MaLoaiPhong = lp.MaLoaiPhong
        WHERE s.MaPhim    = :maPhim
          AND s.NgayChieu = :ngay
          AND (ph.MaRap = :maRap OR s.MaRap = :maRap)
        ORDER BY s.GioChieu
    """, nativeQuery = true)
    List<Map<String, Object>> timSuatChieuConHan(
            @Param("maPhim") String maPhim,
            @Param("maRap")  String maRap,
            @Param("ngay")   LocalDate ngay
    );
}