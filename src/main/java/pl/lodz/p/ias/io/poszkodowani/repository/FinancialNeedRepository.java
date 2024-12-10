package pl.lodz.p.ias.io.poszkodowani.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;

@Repository
public interface FinancialNeedRepository extends JpaRepository<FinancialNeed, Long> {
}
