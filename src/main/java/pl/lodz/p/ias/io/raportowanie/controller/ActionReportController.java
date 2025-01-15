package pl.lodz.p.ias.io.raportowanie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.service.ActionReportService;

import java.util.List;


@CrossOrigin("localhost:5173")
@RestController
@RequestMapping("/api/action-report")
public class ActionReportController {
    private final ActionReportService actionReportService;

    @Autowired
    public ActionReportController(ActionReportService actionReportService) {
        this.actionReportService = actionReportService;
    }

    @PostMapping("/generate")
    public ResponseEntity<GeneratedReport> generateReport(@RequestParam Long userId) {
        return ResponseEntity.ok(actionReportService.generateReport(userId));
    }

    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generateReportPdf(@RequestParam Long userId) {
        byte[] pdfData = actionReportService.generateReportPdf(userId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"action-report.pdf\"")
                .body(pdfData);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<GeneratedReport> getReport(@PathVariable Long reportId) {
        return actionReportService.getReport(reportId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<GeneratedReport>> getAllReports() {
        return ResponseEntity.ok(actionReportService.getAllReports());
    }
}
