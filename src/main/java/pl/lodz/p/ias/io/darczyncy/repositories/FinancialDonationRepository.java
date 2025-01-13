package pl.lodz.p.ias.io.darczyncy.repositories;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialDonationRepository extends JpaRepository<FinancialDonation, Long> {

    List<FinancialDonation> findAllByDonor_Id(Long id);
}
