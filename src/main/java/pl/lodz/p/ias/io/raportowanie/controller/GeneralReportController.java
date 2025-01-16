package pl.lodz.p.ias.io.raportowanie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.service.GeneralReportService;

import java.util.List;

@RestController
@RequestMapping("/api/general-report")
public class GeneralReportController {

    private final GeneralReportService generalReportService;

    @Autowired
    public GeneralReportController(GeneralReportService generalReportService) {
        this.generalReportService = generalReportService;
    }

    @PostMapping("/generate")
    public ResponseEntity<GeneratedReport> generateReport(@RequestParam Long userId) {
        return ResponseEntity.ok(generalReportService.generateReport(userId));
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<GeneratedReport> getReport(@PathVariable Long reportId) {
        return generalReportService.getReport(reportId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

     @GetMapping
     public ResponseEntity<List<GeneratedReport>> getAllReports() {
         return ResponseEntity.ok(generalReportService.getAllReports());
     }
}
