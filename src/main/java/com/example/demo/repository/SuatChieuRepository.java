//import com.example.demo.entity.SuatChieu;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Map;
//
//@Repository
//public interface SuatChieuRepository extends JpaRepository<SuatChieu, String> {
//
//    // Lấy phim có suất chiếu theo ngày + rạp + chưa qua giờ
//    @Query(value = """
//        SELECT DISTINCT
//            p.MaPhim, p.TenPhim, p.MoTa, p.Poster,
//            p.ThoiLuong, p.NgayKhoiChieu,
//            p.DoTuoiPhuHop, p.NgonNgu
//        FROM Phim p
//        JOIN SuatChieu s ON p.MaPhim = s.MaPhim
//        WHERE s.MaRap     = :maRap
//        AND   s.NgayChieu = :ngay
//        AND   DATEADD(SECOND,
//                DATEDIFF(SECOND,'00:00:00', s.GioChieu),
//                CAST(s.NgayChieu AS DATETIME)) > GETDATE()
//        ORDER BY p.TenPhim
//    """, nativeQuery = true)
//    List<Map<String, Object>> timPhimTheoNgayVaRap(
//            @Param("maRap") String maRap,
//            @Param("ngay") LocalDate ngay
//    );
//
//    // Lấy suất chiếu theo phim + ngày + rạp + chưa qua giờ
//    @Query(value = """
//        SELECT
//            s.MaSuat,
//            CONVERT(VARCHAR(5), s.GioChieu, 108) as GioChieu,
//            s.Gia,
//            ph.MaPhong,
//            ph.TenPhong,
//            lp.TenLoaiPhong,
//            ph.SucChua as TongGhe,
//            (SELECT COUNT(*) FROM Ve v
//             WHERE v.MaSuat = s.MaSuat
//             AND   v.TrangThaiVe = 'TRONG') as SoGheTrong
//        FROM SuatChieu s
//        JOIN PhongChieu ph ON s.MaPhong     = ph.MaPhong
//        JOIN LoaiPhong  lp ON ph.MaLoaiPhong = lp.MaLoaiPhong
//        WHERE s.MaPhim    = :maPhim
//        AND   s.MaRap     = :maRap
//        AND   s.NgayChieu = :ngay
//        AND   DATEADD(SECOND,
//                DATEDIFF(SECOND,'00:00:00', s.GioChieu),
//                CAST(s.NgayChieu AS DATETIME)) > GETDATE()
//        ORDER BY s.GioChieu
//    """, nativeQuery = true)
//    List<Map<String, Object>> timSuatChieuConHan(
//            @Param("maPhim") String maPhim,
//            @Param("maRap")  String maRap,
//            @Param("ngay")   LocalDate ngay
//    );
//}