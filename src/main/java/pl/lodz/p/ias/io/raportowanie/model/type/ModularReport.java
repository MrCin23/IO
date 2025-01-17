package pl.lodz.p.ias.io.raportowanie.model.type;

import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;

import java.sql.Timestamp;
import java.util.Set;

public class ModularReport extends GeneralReport {

    private int moduleId;
    private String content;
    private Timestamp startTime;
    private Timestamp endTime;
    private Set<String> fields;

    public ModularReport(Long userId, int moduleId, Timestamp startTime, Timestamp endTime, Set<String> fields) {
        super(userId);
        this.moduleId = moduleId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.fields = fields;
    }

    @Override
    public GeneratedReport generate() {
        //kod odpowiedzialny za pobieranie z bazy danych imienia i nazwiska użytkownika o userId

        //kod odpowiedzialny za generowanie teści raportu w oparciu o baze danych
        content = "Raport modularny" + "\n" + "Ilosc akcji: 10";

        return new GeneratedReport(getUserId(), "Jan", "Kowalskii", content);
    }
}
