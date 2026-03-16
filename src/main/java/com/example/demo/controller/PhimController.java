package com.example.demo.controller;

import com.example.demo.entity.Phim;
import com.example.demo.service.PhimService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/phim")
@CrossOrigin(origins = "*")
public class PhimController {

    private final PhimService phimService;

    public PhimController(PhimService phimService) {
        this.phimService = phimService;
    }

    // GET tất cả phim
    @GetMapping
    public List<Phim> getAll() {
        return phimService.getAllPhim();
    }

    // GET 1 phim theo mã
    @GetMapping("/{id}")
    public Phim getById(@PathVariable String id) {
        return phimService.getPhimById(id);
    }

    // POST thêm phim
    @PostMapping
    public Phim create(@RequestBody Phim phim) {
        return phimService.createPhim(phim);
    }

    // PUT cập nhật phim
    @PutMapping("/{id}")
    public Phim update(@PathVariable String id, @RequestBody Phim phim) {
        return phimService.updatePhim(id, phim);
    }

    // DELETE xóa phim
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        phimService.deletePhim(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/poster/{filename}")
    public ResponseEntity<Resource> getPoster(@PathVariable String filename) {
        try {
            // Trỏ đến thư mục static/poster trong project
            Path path = Paths.get("src/main/resources/static/poster/" + filename);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}