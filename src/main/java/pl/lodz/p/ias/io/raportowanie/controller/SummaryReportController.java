package pl.lodz.p.ias.io.raportowanie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.poszkodowani.service.FinancialNeedService;
import pl.lodz.p.ias.io.poszkodowani.service.ManualNeedService;
import pl.lodz.p.ias.io.poszkodowani.service.MaterialNeedService;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.service.SummaryReportService;

@CrossOrigin("localhost:5173")
@RestController
@RequestMapping("/api/summary-report")
public class SummaryReportController {
    // TODO: interfejsy
    private final SummaryReportService summaryReportService;
    private final FinancialNeedService financialNeedService;
    private final ManualNeedService manualNeedService;
    private final MaterialNeedService materialNeedService;

    @Autowired
    public SummaryReportController(SummaryReportService summaryReportService, FinancialNeedService financialNeedService, ManualNeedService manualNeedService, MaterialNeedService materialNeedService) {
        this.summaryReportService = summaryReportService;
        this.financialNeedService = financialNeedService;
        this.manualNeedService = manualNeedService;
        this.materialNeedService = materialNeedService;
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @PostMapping("/generate")
    public ResponseEntity<GeneratedReport> generateReport() {
        return ResponseEntity.ok(summaryReportService.generateReport
                (financialNeedService.getAllNeeds().size(), manualNeedService.getAllNeeds().size(), materialNeedService.getAllNeeds().size()));
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generateReportPdf() {
        byte[] pdfData = summaryReportService.generateReportPdf
                (financialNeedService.getAllNeeds().size(), manualNeedService.getAllNeeds().size(), materialNeedService.getAllNeeds().size());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"summary-report.pdf\"")
                .body(pdfData);
    }
}
