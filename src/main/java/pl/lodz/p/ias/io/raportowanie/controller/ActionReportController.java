package pl.lodz.p.ias.io.raportowanie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.service.ActionReportService;

import java.security.Principal;
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

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @PostMapping("/generate")
    public ResponseEntity<GeneratedReport> generateReport(@RequestParam Long userId) {
        return ResponseEntity.ok(actionReportService.generateReport(userId));
    }

    // TODO: remove
    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @GetMapping("/skphd")
    public String skphd(@RequestParam String debug, Principal principal) {
        System.out.println("SKPHD " + debug + " " + principal.getName());
        return principal.getName();
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generateReportPdf(@RequestParam Long userId) {
        byte[] pdfData = actionReportService.generateReportPdf(userId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"action-report.pdf\"")
                .body(pdfData);
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @GetMapping("/{reportId}")
    public ResponseEntity<GeneratedReport> getReport(@PathVariable Long reportId) {
        return actionReportService.getReport(reportId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @GetMapping
    public ResponseEntity<List<GeneratedReport>> getAllReports() {
        return ResponseEntity.ok(actionReportService.getAllReports());
    }
}
