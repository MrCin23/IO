package pl.lodz.p.ias.io.raportowanie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;

@Repository
public interface GeneratedReportRepository extends JpaRepository<GeneratedReport, Long> {

}
