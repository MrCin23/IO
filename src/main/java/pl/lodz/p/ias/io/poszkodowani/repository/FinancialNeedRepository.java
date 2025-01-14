package pl.lodz.p.ias.io.poszkodowani.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;

import java.util.List;

@Repository
public interface FinancialNeedRepository extends JpaRepository<FinancialNeed, Long> {
    List<FinancialNeed> findByUserId(Long userId);
}
