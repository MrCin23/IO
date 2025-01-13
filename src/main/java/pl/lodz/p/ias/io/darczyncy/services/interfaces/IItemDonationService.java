package pl.lodz.p.ias.io.darczyncy.services.interfaces;

import pl.lodz.p.ias.io.darczyncy.dto.create.ItemDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;

import java.util.List;

public interface IItemDonationService {
    ItemDonation createItemDonation(ItemDonationCreateDTO dto);

    ItemDonation findItemDonationById(long id);

    List<ItemDonation> findAllItemDonations();

    List<ItemDonation> findAllItemDonationsByDonorId(long donorId);

    List<ItemDonation> findAllItemDonationsByWarehouseId(long warehouseId);

    List<ItemDonation> findAllByCurrentUser();

    byte[] createConfirmationPdf(long donationId);

    ItemDonation updateItemDonation(long id, ItemDonation updatedItemDonation);

    void deleteItemDonationById(Long id);
}
