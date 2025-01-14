package pl.lodz.p.ias.io.raportowanie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.service.SummaryReportService;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

@CrossOrigin("*")
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
    public ResponseEntity<byte[]> generateReportPdf(@RequestParam Long userId,
                                                    @RequestParam(required = false) String startTime,
                                                    @RequestParam(required = false) String endTime, @RequestParam Set<String> fields) {

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

        if (fields == null) {
            fields = Set.of("All"); // Domyślny zestaw pól
        }
        byte[] pdfData = summaryReportService.generateReportPdf(userId, startTimestamp, endTimestamp, fields);

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
