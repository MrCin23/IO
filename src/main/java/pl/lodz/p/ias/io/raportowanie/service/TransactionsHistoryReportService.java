package pl.lodz.p.ias.io.raportowanie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.model.type.TransactionsHistoryReport;
import pl.lodz.p.ias.io.raportowanie.repository.GeneratedReportRepository;
import pl.lodz.p.ias.io.raportowanie.utils.ReportGenerator;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.AccountRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TransactionsHistoryReportService {
    private final GeneratedReportRepository generatedReportRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionsHistoryReportService(GeneratedReportRepository generatedReportRepository, AccountRepository accountRepository) {
        this.generatedReportRepository = generatedReportRepository;
        this.accountRepository = accountRepository;
    }

    public GeneratedReport generateReport(Timestamp startTime, Timestamp endTime, List<FinancialDonation> allForCurrentUser) {
        //kod odpowiedzialny za pobranie aktualnego uzytkownika
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account currentUser = accountRepository.findByUsername(auth.getName());

        TransactionsHistoryReport report = new TransactionsHistoryReport(currentUser.getId(), startTime, endTime);

        return generatedReportRepository.save
                (ReportGenerator.generateTransactionHistoryReport
                        (currentUser.getFirstName(), currentUser.getLastName(), report, allForCurrentUser));
    }

    public byte[] generateReportPdf(Timestamp startTime, Timestamp endTime, List<FinancialDonation> allForCurrentUser) {
        //kod odpowiedzialny za pobranie aktualnego uzytkownika
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account currentUser = accountRepository.findByUsername(auth.getName());

        TransactionsHistoryReport report = new TransactionsHistoryReport(currentUser.getId(), startTime, endTime);
        return ReportGenerator.generateTransactionHistoryReport
                        (currentUser.getFirstName(), currentUser.getLastName(), report, allForCurrentUser).toPdf();
    }
}
