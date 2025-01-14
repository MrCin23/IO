package pl.lodz.p.ias.io.raportowanie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.service.ActionsHistoryReportService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/actions-history-report")
public class ActionsHistoryReportController {
    private final ActionsHistoryReportService actionsHistoryReportService;

    @Autowired
    public ActionsHistoryReportController(ActionsHistoryReportService actionsHistoryReportService) {
        this.actionsHistoryReportService = actionsHistoryReportService;
    }

    @PostMapping("/generate")
    public ResponseEntity<GeneratedReport> generateReport(@RequestParam Long userId) {
        return ResponseEntity.ok(actionsHistoryReportService.generateReport(userId));
    }

    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generateReportPdf(@RequestParam Long userId) {
        byte[] pdfData = actionsHistoryReportService.generateReportPdf(userId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"actions-history-report.pdf\"")
                .body(pdfData);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<GeneratedReport> getReport(@PathVariable Long reportId) {
        return actionsHistoryReportService.getReport(reportId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<GeneratedReport>> getAllReports() {
        return ResponseEntity.ok(actionsHistoryReportService.getAllReports());
    }
}
