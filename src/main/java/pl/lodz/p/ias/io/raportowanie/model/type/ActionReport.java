package pl.lodz.p.ias.io.raportowanie.model.type;

import lombok.Getter;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;

@Getter
public class ActionReport extends GeneralReport {

    private String content;

    public ActionReport(Long userId) {
        super(userId);
    }

    @Override
    public GeneratedReport generate() {
        //kod odpowiedzialny za pobieranie z bazy danych imienia i nazwiska użytkownika o userId

        //kod odpowiedzialny za generowanie teści raportu w oparciu o baze danych
        content = "Raport akcji" + "\n" + "Ilosc akcji: 10";

        return new GeneratedReport(getUserId(), content);
    }
}
