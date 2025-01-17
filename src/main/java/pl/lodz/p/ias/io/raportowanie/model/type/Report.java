package pl.lodz.p.ias.io.raportowanie.model.type;

import lombok.Getter;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;

public abstract class Report {

    @Getter
    private Long userId;

    public Report(Long userId) {
        this.userId = userId;
    }

    public abstract GeneratedReport generate();
}
