package pl.lodz.p.ias.io.darczyncy.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.darczyncy.dto.create.ItemDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.exceptions.ItemDonationNotFoundException;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;
import pl.lodz.p.ias.io.darczyncy.repositories.ItemDonationRepository;
import pl.lodz.p.ias.io.darczyncy.services.interfaces.IItemDonationService;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;
import pl.lodz.p.ias.io.poszkodowani.repository.MaterialNeedRepository;

import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.UserRepository;

@RequiredArgsConstructor
@Service
public class ItemDonationService implements IItemDonationService {

    private final ItemDonationRepository itemDonationRepository;

    private final MaterialNeedRepository materialNeedRepository;

    private final UserRepository userRepository;

    @Override
    public ItemDonation createItemDonation(ItemDonationCreateDTO dto) {

        Account donor = userRepository.findById(dto.donorId()).orElse(null);
        MaterialNeed need = materialNeedRepository.findById(dto.needId()).orElse(null);

        ItemDonation itemDonation = new ItemDonation(
                donor,
                need,
                dto.itemName(),
                dto.resourceQuantity(),
                dto.warehouseId(),
                ItemDonation.ItemCategory.valueOf(dto.category()),
                dto.description()
        );
        return itemDonationRepository.save(itemDonation);
    }

    @Override
    public ItemDonation findItemDonationById(long id) {
        ItemDonation itemDonation = itemDonationRepository.findById(id).orElse(null);
        if (itemDonation == null) {
            throw new ItemDonationNotFoundException();
        }
        return itemDonation;
    }
}
