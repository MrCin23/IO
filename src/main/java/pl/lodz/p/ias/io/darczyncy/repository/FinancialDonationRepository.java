package pl.lodz.p.ias.io.darczyncy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;

public interface FinancialDonationRepository extends JpaRepository<FinancialDonation, Long> {
    FinancialDonation findById(long id);
}
