package pl.lodz.p.ias.io.darczyncy.services.interfaces;

import pl.lodz.p.ias.io.darczyncy.dto.create.ItemDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;

import java.util.List;

public interface IItemDonationService {
    ItemDonation createDonation(ItemDonationCreateDTO dto);

    ItemDonation findById(long id);

    List<ItemDonation> findAllItemDonations();

    List<ItemDonation> findAllByDonorId(long donorId);

    List<ItemDonation> findAllByWarehouseId(long warehouseId);

    List<ItemDonation> findAllByCurrentUser();

    byte[] createConfirmationPdf(String language, long donationId);

    ItemDonation update(long id, ItemDonation updatedItemDonation);

    void deleteById(Long id);
}
