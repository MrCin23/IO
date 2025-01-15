package pl.lodz.p.ias.io.raportowanie.model.type;

import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;

public class ActionsHistoryReport extends Report {

    private String content;

    public ActionsHistoryReport(Long userId) {
        super(userId);
    }

    public String getContent() {
        return content;
    }

    @Override
    public GeneratedReport generate() {

        //kod odpowiedzialny za pobieranie z bazy danych imienia i nazwiska użytkownika o userId

        //kod odpowiedzialny za generowanie teści raportu w oparciu o baze danych
        content = "Raport historii akcji" + "\n" + "Ilosc akcji: 10";

        return new GeneratedReport(getUserId(), "Jan", "Kowalskii", content);
    }
}
