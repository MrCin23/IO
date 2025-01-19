package pl.lodz.p.ias.io.raportowanie.utils;

import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.model.type.TransactionsHistoryReport;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;

public abstract class ReportGenerator {
    public static GeneratedReport generateTransactionHistoryReport(String firstName, String lastName, TransactionsHistoryReport report) {
        //kod odpowiedzialny za generowanie treści raportu w oparciu o baze danych
        String content = "Przedzial czasowy: "+report.getStartTime()+" - " + report.getEndTime() + "\n" + "Raport historii transakcji" + "\n" + "Ilosc transakcji: 10";

        return new GeneratedReport(firstName, lastName, content);
    }
}
