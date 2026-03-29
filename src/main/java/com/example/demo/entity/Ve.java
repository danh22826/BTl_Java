import com.example.demo.entity.Ghe;
import com.example.demo.entity.SuatChieu;
import jakarta.persistence.*;

@Entity
@Table(
        name = "Ve",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_suat_ghe", columnNames = {"MaSuat", "MaGhe"})
        }
)
public class Ve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MaSuat", nullable = false)
    private SuatChieu suatChieu;

    @ManyToOne
    @JoinColumn(name = "MaGhe", nullable = false)
    private Ghe ghe;

    @Column(name = "TrangThaiVe")
    private String trangThaiVe; // TRONG / DA_DAT
}