package pl.lodz.p.ias.io.raportowanie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.darczyncy.services.interfaces.IFinancialDonationService;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.service.TransactionsHistoryReportService;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

@CrossOrigin("localhost:5173")
@RestController
@RequestMapping("/api/transaction-history-report")
public class TransactionsHistoryReportController {
    private final TransactionsHistoryReportService transactionsHistoryReportService;
    private final IFinancialDonationService financialDonationService;

    @Autowired
    public TransactionsHistoryReportController(TransactionsHistoryReportService transactionsHistoryReportService, IFinancialDonationService financialDonationService) {
        this.transactionsHistoryReportService = transactionsHistoryReportService;
        this.financialDonationService = financialDonationService;
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'DARCZYŃCA')")
    @PostMapping("/generate")
    public ResponseEntity<GeneratedReport> generateReport(@RequestParam Timestamp startDate, @RequestParam Timestamp endDate) {

        return ResponseEntity.ok(transactionsHistoryReportService.generateReport(startDate, endDate, financialDonationService.findAllForCurrentUser()));
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'DARCZYŃCA')")
    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generateReportPdf(@RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        // Konwersja startTime i endTime na Timestamp
        Timestamp startTimestamp = null;
        Timestamp endTimestamp = null;

        byte[] error = "Error".getBytes();

        if (startTime != null) {
            try {
                startTimestamp = new Timestamp(dateFormat.parse(startTime).getTime());
            } catch (ParseException e) {
                return ResponseEntity.badRequest().body(error);
            }
        } else {
            startTimestamp = new Timestamp(0); // Ustaw domyślną datę
        }

        if (endTime != null) {
            try {
                endTimestamp = new Timestamp(dateFormat.parse(endTime).getTime());
            } catch (ParseException e) {
                return ResponseEntity.badRequest().body(error);
            }
        } else {
            endTimestamp = new Timestamp(System.currentTimeMillis()); // Ustaw bieżącą datę
        }

        byte[] pdfData = transactionsHistoryReportService.generateReportPdf(startTimestamp, endTimestamp, financialDonationService.findAllForCurrentUser());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"transaction-history-report.pdf\"")
                .body(pdfData);
    }
}
