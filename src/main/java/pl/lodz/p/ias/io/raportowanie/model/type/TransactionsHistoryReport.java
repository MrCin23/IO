package pl.lodz.p.ias.io.raportowanie.model.type;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class TransactionsHistoryReport extends Report {
    private Timestamp startTime;
    private Timestamp endTime;

    public TransactionsHistoryReport(Long userId, Timestamp startTime, Timestamp endTime) {
        super(userId);
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
