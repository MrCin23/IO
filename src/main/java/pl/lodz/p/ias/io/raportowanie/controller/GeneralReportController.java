package pl.lodz.p.ias.io.raportowanie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.service.GeneralReportService;

import java.util.List;

@CrossOrigin("localhost:5173")
@RestController
@RequestMapping("/api/general-report")
public class GeneralReportController {

    private final GeneralReportService generalReportService;

    @Autowired
    public GeneralReportController(GeneralReportService generalReportService) {
        this.generalReportService = generalReportService;
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @PostMapping("/generate")
    public ResponseEntity<GeneratedReport> generateReport(@RequestParam Long userId) {
        return ResponseEntity.ok(generalReportService.generateReport(userId));
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generateReportPdf(@RequestParam Long userId) {
        byte[] pdfData = generalReportService.generateReportPdf(userId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"general-report.pdf\"")
                .body(pdfData);
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @GetMapping("/{reportId}")
    public ResponseEntity<GeneratedReport> getReport(@PathVariable Long reportId) {
        return generalReportService.getReport(reportId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
     @GetMapping
     public ResponseEntity<List<GeneratedReport>> getAllReports() {
         return ResponseEntity.ok(generalReportService.getAllReports());
     }
}
