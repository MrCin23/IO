package pl.lodz.p.ias.io.raportowanie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.model.type.ActionsHistoryReport;
import pl.lodz.p.ias.io.raportowanie.repository.GeneratedReportRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ActionsHistoryReportService {
    private final GeneratedReportRepository generatedReportRepository;

    @Autowired
    public ActionsHistoryReportService(GeneratedReportRepository generatedReportRepository) {
        this.generatedReportRepository = generatedReportRepository;
    }

    public GeneratedReport generateReport(Long userId) {
        //kod odpowiedzialny za sprawdzenie czy istnieje uztkownik o userId i czy ma odpowiednią role

        return generatedReportRepository.save(new ActionsHistoryReport(userId).generate());
    }
    // nwm czy to jest git
    public byte[] generateReportPdf(Long userId) {
        return new ActionsHistoryReport(userId).generate().toPdf();
    }

    public Optional<GeneratedReport> getReport(Long reportId) {
        return generatedReportRepository.findById(reportId);
    }

    public List<GeneratedReport> getAllReports() {
        return generatedReportRepository.findAll();
    }
}
