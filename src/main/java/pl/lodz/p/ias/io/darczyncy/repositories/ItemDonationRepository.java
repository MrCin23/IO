package pl.lodz.p.ias.io.darczyncy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemDonationRepository extends JpaRepository<ItemDonation, Long> {

    List<ItemDonation> findAllByDonor_Id(Long donorId);

    List<ItemDonation> findAllByWarehouseId(Long warehouseId);

    Optional<ItemDonation> findByIdAndDonor_Username(long id, String donorUsername);
}
