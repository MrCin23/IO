package pl.lodz.p.ias.io.raportowanie.model.type;

import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.query.GeneralReportQuery;

import java.util.Iterator;
import java.util.Set;

public class GeneralReport extends Report {

    private String content;

    public GeneralReport(Long userId) {
        super(userId);
    }

    public String getContent() {
        return content;
    }

    public GeneratedReport generate() {

        GeneralReportQuery grq = new GeneralReportQuery();
        int usersCount = grq.countUsers();

        content = "Raport ogolny" + "\n"
                + "Ilosc uzytkownikow: " + usersCount
        + "\n\nIlosc potrzeb: " + "\n"
        + "  - Finansowych: " + grq.countFinancialNeeds() + "\n"
        + "  - Manualnych: " + grq.countManualNeeds() + "\n"
        + "  - Materialnych: " + grq.countMaterialNeeds() + "\n";

        //return new GeneratedReport(getUserId(), content);
        return null;
    }
}
