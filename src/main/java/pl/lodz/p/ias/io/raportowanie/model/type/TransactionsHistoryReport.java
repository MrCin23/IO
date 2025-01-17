package pl.lodz.p.ias.io.raportowanie.model.type;

import lombok.Getter;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;

import java.sql.Timestamp;

@Getter
public class TransactionsHistoryReport extends Report {


    private String content;
    private Timestamp startTime;
    private Timestamp endTime;

    public TransactionsHistoryReport(Long userId, Timestamp startTime, Timestamp endTime) {
        super(userId);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public GeneratedReport generate() {

        //kod odpowiedzialny za pobieranie z bazy danych imienia i nazwiska użytkownika o userId

        //kod odpowiedzialny za generowanie teści raportu w oparciu o baze danych
        content = "Przedzial czasowy: "+startTime+" - " + endTime + "\n" + "Raport historii transakcji" + "\n" + "Ilosc transakcji: 10";

        return new GeneratedReport(getUserId(), "Jan", "Kowalskii", content);
    }
}
