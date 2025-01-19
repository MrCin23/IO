package pl.lodz.p.ias.io.raportowanie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.model.type.SummaryReport;
import pl.lodz.p.ias.io.raportowanie.repository.GeneratedReportRepository;
import pl.lodz.p.ias.io.raportowanie.utils.ReportGenerator;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.AccountRepository;

@Service
public class SummaryReportService {
    private final GeneratedReportRepository generatedReportRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public SummaryReportService(GeneratedReportRepository generatedReportRepository, AccountRepository accountRepository) {
        this.generatedReportRepository = generatedReportRepository;
        this.accountRepository = accountRepository;
    }

    public GeneratedReport generateReport(int financialNeedsCount, int manualNeedsCount, int materialNeedsCount) {
        //kod odpowiedzialny za pobranie aktualnego uzytkownika
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account currentUser = accountRepository.findByUsername(auth.getName());

        long usersCount = accountRepository.count();

        SummaryReport report = new SummaryReport(currentUser.getId());

        return generatedReportRepository.save
                (ReportGenerator.generateSummaryReport
                        (currentUser.getFirstName(), currentUser.getLastName(), (int)usersCount, financialNeedsCount, manualNeedsCount, materialNeedsCount));
    }

    public byte[] generateReportPdf(int financialNeedsCount, int manualNeedsCount, int materialNeedsCount) {
        //kod odpowiedzialny za pobranie aktualnego uzytkownika
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account currentUser = accountRepository.findByUsername(auth.getName());

        long usersCount = accountRepository.count();

        SummaryReport report = new SummaryReport(currentUser.getId());

        return ReportGenerator.generateSummaryReport
                (currentUser.getFirstName(), currentUser.getLastName(), (int)usersCount, financialNeedsCount, manualNeedsCount, materialNeedsCount).toPdf();
    }
}
