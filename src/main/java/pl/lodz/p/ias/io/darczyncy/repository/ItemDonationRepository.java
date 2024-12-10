package pl.lodz.p.ias.io.darczyncy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;

public interface ItemDonationRepository extends JpaRepository<ItemDonation, Long> {
    ItemDonation findById(long id);

}
