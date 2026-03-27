import com.example.demo.entity.ThanhPho;
import com.example.demo.service.ThanhPhoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ThanhPhoController {

    private final ThanhPhoService thanhPhoService;

    public ThanhPhoController(ThanhPhoService thanhPhoService) {
        this.thanhPhoService = thanhPhoService;
    }

    @GetMapping("/thanh-pho")
    public List<ThanhPho> getThanhPho() {
        return thanhPhoService.getAllThanhPho();
    }
}