package pl.lodz.p.ias.io.darczyncy.services.interfaces;

import pl.lodz.p.ias.io.darczyncy.dto.create.ItemDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;

import java.util.List;

public interface IItemDonationService {
    ItemDonation createItemDonation(ItemDonationCreateDTO dto);

    ItemDonation findItemDonationById(long id);

    List<ItemDonation> findAllItemDonations();

    List<ItemDonation> findItemDonationsByDonorId(long donorId);

    ItemDonation updateItemDonation(long id, ItemDonation updatedItemDonation);

    void deleteItemDonationById(Long id);
}
