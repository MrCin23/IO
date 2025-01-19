package pl.lodz.p.ias.io.raportowanie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.model.type.ModularReport;
import pl.lodz.p.ias.io.raportowanie.repository.GeneratedReportRepository;
import pl.lodz.p.ias.io.raportowanie.utils.ReportGenerator;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.AccountRepository;

@Service
public class ModularReportService {
    private final GeneratedReportRepository generatedReportRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ModularReportService(GeneratedReportRepository generatedReportRepository, AccountRepository accountRepository) {
        this.generatedReportRepository = generatedReportRepository;
        this.accountRepository = accountRepository;
    }

    public GeneratedReport generateReport(int moduleId) {
        //kod odpowiedzialny za pobranie aktualnego uzytkownika
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account currentUser = accountRepository.findByUsername(auth.getName());

        ModularReport report = new ModularReport(currentUser.getId(), moduleId);

        return generatedReportRepository.save
                (ReportGenerator.generateModularReport
                        (currentUser.getFirstName(), currentUser.getLastName(), report));

    }
    public byte[] generateReportPdf(int moduleId) {
        //kod odpowiedzialny za pobranie aktualnego uzytkownika
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account currentUser = accountRepository.findByUsername(auth.getName());

        ModularReport report = new ModularReport(currentUser.getId(), moduleId);

        return ReportGenerator.generateModularReport
                        (currentUser.getFirstName(), currentUser.getLastName(), report).toPdf();
    }
}
