package pl.lodz.p.ias.io.wolontariusze.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dupa")
public class DupaController {

    @PostMapping
    public ResponseEntity<String> postDupa() {
        return ResponseEntity.ok("DOOPA");
    }

    @GetMapping("/{receiver}")
    public ResponseEntity<String> getDupa(@PathVariable String receiver) {
        return ResponseEntity.ok(receiver);
    }
}