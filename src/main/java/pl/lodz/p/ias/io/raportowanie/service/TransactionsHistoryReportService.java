package pl.lodz.p.ias.io.raportowanie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.model.type.GeneralReport;
import pl.lodz.p.ias.io.raportowanie.model.type.TransactionsHistoryReport;
import pl.lodz.p.ias.io.raportowanie.repository.GeneratedReportRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionsHistoryReportService {
    private final GeneratedReportRepository generatedReportRepository;

    @Autowired
    public TransactionsHistoryReportService(GeneratedReportRepository generatedReportRepository) {
        this.generatedReportRepository = generatedReportRepository;
    }

    public GeneratedReport generateReport(Long userId, Timestamp startTime, Timestamp endTime) {
        //kod odpowiedzialny za sprawdzenie czy istnieje uztkownik o userId i czy ma odpowiednią role

        return generatedReportRepository.save(new TransactionsHistoryReport(userId, startTime, endTime).generate());
    }
    // nwm czy to jest git
    public byte[] generateReportPdf(Long userId, Timestamp startTime, Timestamp endTime) {
        return new TransactionsHistoryReport(userId, startTime, endTime).generate().toPdf();
    }

    public Optional<GeneratedReport> getReport(Long reportId) {
        return generatedReportRepository.findById(reportId);
    }

    public List<GeneratedReport> getAllReports() {
        return generatedReportRepository.findAll();
    }
}
