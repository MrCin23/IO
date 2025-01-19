package pl.lodz.p.ias.io.raportowanie.model.type;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;

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
}
