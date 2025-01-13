package pl.lodz.p.ias.io.raportowanie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.service.SummaryReportService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/summary-report")
public class SummaryReportController {
    private final SummaryReportService summaryReportService;

    @Autowired
    public SummaryReportController(SummaryReportService summaryReportService) {
        this.summaryReportService = summaryReportService;
    }

    @PostMapping("/generate")
    public ResponseEntity<GeneratedReport> generateReport(@RequestParam Long userId, @RequestParam Timestamp startDate, @RequestParam Timestamp endDate, @RequestParam Set<String> fields) {
        return ResponseEntity.ok(summaryReportService.generateReport(userId, startDate, endDate, fields));
    }

    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generateReportPdf(@RequestParam Long userId, @RequestParam Timestamp startDate, @RequestParam Timestamp endDate, @RequestParam Set<String> fields) {
        byte[] pdfData = summaryReportService.generateReportPdf(userId, startDate, endDate, fields);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"summary-report.pdf\"")
                .body(pdfData);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<GeneratedReport> getReport(@PathVariable Long reportId) {
        return summaryReportService.getReport(reportId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<GeneratedReport>> getAllReports() {
        return ResponseEntity.ok(summaryReportService.getAllReports());
    }
}
