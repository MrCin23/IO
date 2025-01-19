package pl.lodz.p.ias.io.raportowanie.model.type;

import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;

import java.sql.Timestamp;
import java.util.Set;

public class SummaryReport extends GeneralReport {

    private String content;
    private Timestamp startTime;
    private Timestamp endTime;
    private Set<String> fields;

    public SummaryReport(Long userId, Timestamp startTime, Timestamp endTime, Set<String> fields) {
        super(userId);
        this.startTime = startTime;
        this.endTime = endTime;
        this.fields = fields;
    }

    public GeneratedReport generate() {
        //kod odpowiedzialny za pobieranie z bazy danych imienia i nazwiska użytkownika o userId

        //kod odpowiedzialny za generowanie teści raportu w oparciu o baze danych
        content = "Przedzial czasowy: "+startTime+" - " + endTime + "\n" + "Raport podsumowujacy" + "\n" + "Ilosc akcji: 10";

        //return new GeneratedReport(getUserId(), content);
        return null;
    }
}
