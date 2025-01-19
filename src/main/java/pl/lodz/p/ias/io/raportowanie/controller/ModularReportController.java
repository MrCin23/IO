package pl.lodz.p.ias.io.raportowanie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.service.ModularReportService;

@CrossOrigin("localhost:5173")
@RestController
@RequestMapping("/api/modular-report")
public class ModularReportController {
    private final ModularReportService modularReportService;

    @Autowired
    public ModularReportController(ModularReportService modularReportService) {
        this.modularReportService = modularReportService;
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @PostMapping("/generate")
    public ResponseEntity<GeneratedReport> generateReport(@RequestParam int moduleId) {
        return ResponseEntity.ok(modularReportService.generateReport(moduleId));
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generateReportPdf(@RequestParam int moduleId) {
        byte[] pdfData = modularReportService.generateReportPdf(moduleId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"modular-report.pdf\"")
                .body(pdfData);
    }
}
