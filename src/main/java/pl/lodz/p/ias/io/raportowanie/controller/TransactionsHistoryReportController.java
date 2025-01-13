package pl.lodz.p.ias.io.raportowanie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.service.TransactionsHistoryReportService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/transaction-history-report")
public class TransactionsHistoryReportController {
    private final TransactionsHistoryReportService transactionsHistoryReportService;

    @Autowired
    public TransactionsHistoryReportController(TransactionsHistoryReportService transactionsHistoryReportService) {
        this.transactionsHistoryReportService = transactionsHistoryReportService;
    }

    @PostMapping("/generate")
    public ResponseEntity<GeneratedReport> generateReport(@RequestParam Long userId, @RequestParam Timestamp startDate, @RequestParam Timestamp endDate) {
        return ResponseEntity.ok(transactionsHistoryReportService.generateReport(userId, startDate, endDate));
    }

    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generateReportPdf(@RequestParam Long userId, @RequestParam Timestamp startDate, @RequestParam Timestamp endDate) {
        byte[] pdfData = transactionsHistoryReportService.generateReportPdf(userId, startDate, endDate);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"transaction-history-report.pdf\"")
                .body(pdfData);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<GeneratedReport> getReport(@PathVariable Long reportId) {
        return transactionsHistoryReportService.getReport(reportId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<GeneratedReport>> getAllReports() {
        return ResponseEntity.ok(transactionsHistoryReportService.getAllReports());
    }
}
