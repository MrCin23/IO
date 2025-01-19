package pl.lodz.p.ias.io.raportowanie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.service.ModularReportService;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

@CrossOrigin("localhost:5173")
@RestController
@RequestMapping("/api/modular-report")
public class ModularReportController {
    private final ModularReportService generalReportService;

    @Autowired
    public ModularReportController(ModularReportService generalReportService) {
        this.generalReportService = generalReportService;
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @PostMapping("/generate")
    public ResponseEntity<GeneratedReport> generateReport(@RequestParam Long userId, @RequestParam int moduleId, @RequestParam(required = false) Timestamp startTime, @RequestParam(required = false) Timestamp endTime, @RequestParam(required = false) Set<String> fields) {

        if(startTime == null || endTime == null || fields == null) {
            startTime = new Timestamp(0);
            endTime = new Timestamp(System.currentTimeMillis());
            fields = Set.of("All");
        }

        return ResponseEntity.ok(generalReportService.generateReport(userId, moduleId, startTime, endTime, fields));
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generateReportPdf(@RequestParam Long userId,
                                                    @RequestParam int moduleId,
                                                    @RequestParam(required = false) String startTime,
                                                    @RequestParam(required = false) String endTime,
                                                    @RequestParam(required = false) Set<String> fields) {
        // Format daty ISO 8601
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

        byte[] pdfData = generalReportService.generateReportPdf(userId, moduleId, startTimestamp, endTimestamp, fields);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"modular-report.pdf\"")
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
