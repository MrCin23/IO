package pl.lodz.p.ias.io.raportowanie.utils;

import pl.lodz.p.ias.io.darczyncy.model.Donation;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.model.type.TransactionsHistoryReport;

import java.util.List;

public abstract class ReportGenerator {
    public static GeneratedReport generateTransactionHistoryReport(String firstName, String lastName, TransactionsHistoryReport report, List<FinancialDonation> allForCurrentUser) {
        //kod odpowiedzialny za generowanie treści raportu w oparciu o interfejs darczyncow

        String transactionList = "";
        int transactionCount = 0;

        for (FinancialDonation donation : allForCurrentUser) {
            if (donation.getDonationDate().isAfter(report.getStartTime().toLocalDateTime().toLocalDate())
               && donation.getDonationDate().isBefore(report.getEndTime().toLocalDateTime().toLocalDate())) {
                transactionList += "---\n" + donation.getNeed().getDescription() + " | " + donation.getAmount() + " " + donation.getCurrency() + " | " + donation.getDonationDate();
                transactionCount++;
            }
        }

        String content = "Przedzial czasowy: "+report.getStartTime()+" - " + report.getEndTime() + "\n" + "Raport historii transakcji" + "\n" + transactionList+  "\n" + "Ilosc transakcji: " + transactionCount;

        return new GeneratedReport(firstName, lastName, content);
    }
}
