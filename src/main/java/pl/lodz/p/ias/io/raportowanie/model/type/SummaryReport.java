package pl.lodz.p.ias.io.raportowanie.model.type;

import lombok.Getter;

@Getter
public class SummaryReport extends Report {
    public SummaryReport(Long userId) {
        super(userId);
    }
}
