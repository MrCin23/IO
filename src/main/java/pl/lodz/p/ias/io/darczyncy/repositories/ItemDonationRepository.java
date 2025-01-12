package pl.lodz.p.ias.io.darczyncy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;

import java.util.List;

@Repository
public interface ItemDonationRepository extends JpaRepository<ItemDonation, Long> {

    List<ItemDonation> findAllByDonor_Id(Long donorId);
}
