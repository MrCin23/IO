package pl.lodz.p.ias.io.darczyncy.service.interfaces;

import pl.lodz.p.ias.io.darczyncy.dto.create.ItemDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;

public interface IItemDonationService {
    ItemDonation createItemDonation(ItemDonationCreateDTO dto);

    ItemDonation findItemDonationById(long id);
}
