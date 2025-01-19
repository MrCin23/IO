package pl.lodz.p.ias.io.raportowanie.utils;

import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.model.type.ModularReport;
import pl.lodz.p.ias.io.raportowanie.model.type.TransactionsHistoryReport;
import pl.lodz.p.ias.io.raportowanie.query.SummaryReportQuery;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class ReportGenerator {
    public static GeneratedReport generateTransactionHistoryReport(String firstName, String lastName, TransactionsHistoryReport report, List<FinancialDonation> allForCurrentUser) {
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

    public static GeneratedReport generateSummaryReport(String firstName, String lastName, int usersCount, int financialNeedsCount, int manualNeedsCount, int materialNeedsCount) {
        String content = "Raport calosciowy, stan na " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + "\n"
                + "Ilosc uzytkownikow: " + usersCount
                + "\n\nIlosc potrzeb: " + "\n"
                + "  - Finansowych: " + financialNeedsCount + "\n"
                + "  - Manualnych: " + manualNeedsCount + "\n"
                + "  - Materialnych: " + materialNeedsCount + "\n";

        return new GeneratedReport(firstName, lastName, content);
    }

    public static GeneratedReport generateModularReport(String firstName, String lastName, ModularReport report) {
        SummaryReportQuery srq = new SummaryReportQuery();

        String content = "Raport modulowy, stan na " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + "\n---\n";

        switch (report.getModuleId()) {
            case 1: {
                content += srq.resourcesQuery();
                break;
            }
            case 2: {
                content += srq.warehousesQuery();
                break;
            }
            case 3: {
                content += srq.productsQuery();
                break;
            }
            case 4: {
                content += srq.rolesQuery();
                break;
            }
            case 5: {
                content += srq.volunteerGroupsQuery();
                break;
            }
            default: {
                content += "[modul nie istnieje]";
                break;
            }
        }

        return new GeneratedReport(firstName, lastName, content);
    }
}
