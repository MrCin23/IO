package pl.lodz.p.ias.io.zasoby.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.zasoby.service.ResourceReportService;

@RestController
@RequestMapping("/api/reports")
public class ResourceReportController {

    private final ResourceReportService reportService;

    @Autowired
    public ResourceReportController(ResourceReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/usage")
    @ResponseStatus(HttpStatus.OK)
    public String getResourceUsageReport() {
        return reportService.generateResourceUsageReport();
    }

    @GetMapping("/usage/export")
    @ResponseStatus(HttpStatus.OK)
    public byte[] exportResourceUsageReport(HttpServletResponse response) {
        String report = reportService.generateResourceUsageReport();

        byte[] reportBytes = report.getBytes();
        response.setHeader("Content-Disposition", "attachment; filename=resource_report.txt");
        return reportBytes;
    }
}