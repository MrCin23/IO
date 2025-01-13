package pl.lodz.p.ias.io.raportowanie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.model.type.ActionReport;
import pl.lodz.p.ias.io.raportowanie.repository.GeneratedReportRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ActionReportService {
    private final GeneratedReportRepository generatedReportRepository;

    @Autowired
    public ActionReportService(GeneratedReportRepository generatedReportRepository) {
        this.generatedReportRepository = generatedReportRepository;
    }

    public GeneratedReport generateReport(Long userId) {
        //kod odpowiedzialny za sprawdzenie czy istnieje uztkownik o userId i czy ma odpowiednią role

        return generatedReportRepository.save(new ActionReport(userId).generate());
    }

    public byte[] generateReportPdf(Long userId) {
        return new ActionReport(userId).generate().toPdf();
    }

    public Optional<GeneratedReport> getReport(Long reportId) {
        return generatedReportRepository.findById(reportId);
    }

    public List<GeneratedReport> getAllReports() {
        return generatedReportRepository.findAll();
    }
}
