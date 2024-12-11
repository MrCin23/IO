package pl.lodz.p.ias.io.darczyncy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;

@Repository
public interface ItemDonationRepository extends JpaRepository<ItemDonation, Long> {
}
