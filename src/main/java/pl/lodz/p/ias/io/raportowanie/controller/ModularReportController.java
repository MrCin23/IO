package pl.lodz.p.ias.io.raportowanie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.service.ModularReportService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/modular-report")
public class ModularReportController {
    private final ModularReportService generalReportService;

    @Autowired
    public ModularReportController(ModularReportService generalReportService) {
        this.generalReportService = generalReportService;
    }

    @PostMapping("/generate")
    public ResponseEntity<GeneratedReport> generateReport(@RequestParam Long userId, @RequestParam int moduleId, @RequestParam(required = false) Timestamp startTime, @RequestParam(required = false) Timestamp endTime, @RequestParam(required = false) Set<String> fields) {

        if(startTime == null || endTime == null || fields == null) {
            startTime = new Timestamp(0);
            endTime = new Timestamp(System.currentTimeMillis());
            fields = Set.of("All");
        }

        return ResponseEntity.ok(generalReportService.generateReport(userId, moduleId, startTime, endTime, fields));
    }

    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generateReportPdf(@RequestParam Long userId, @RequestParam int moduleId, @RequestParam(required = false) Timestamp startTime, @RequestParam(required = false) Timestamp endTime, @RequestParam(required = false) Set<String> fields) {

        if(startTime == null || endTime == null || fields == null) {
            startTime = new Timestamp(0);
            endTime = new Timestamp(System.currentTimeMillis());
            fields = Set.of("All");
        }

        byte[] pdfData = generalReportService.generateReportPdf(userId, moduleId, startTime, endTime, fields);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"modular-report.pdf\"")
                .body(pdfData);
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
