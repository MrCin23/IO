package pl.lodz.p.ias.io.darczyncy.service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.darczyncy.dto.create.ItemDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.exceptions.ItemDonationNotFoundException;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;
import pl.lodz.p.ias.io.darczyncy.repository.ItemDonationRepository;
import pl.lodz.p.ias.io.darczyncy.service.interfaces.IItemDonationService;

@RequiredArgsConstructor
@Service
public class ItemDonationService implements IItemDonationService {

    private final ItemDonationRepository itemDonationRepository;

    @Override
    public ItemDonation createItemDonation(ItemDonationCreateDTO dto) {
        ItemDonation itemDonation = new ItemDonation(
                dto.donor(),
                dto.need(),
                dto.itemName(),
                dto.resourceQuantity(),
                dto.warehouseId(),
                dto.category(),
                dto.description()
        );
        return itemDonationRepository.save(itemDonation);
    }

    @Override
    public ItemDonation findItemDonationById(long id) {
        ItemDonation itemDonation = itemDonationRepository.findById(id);
        if (itemDonation == null) {
            throw new ItemDonationNotFoundException();
        }
        return itemDonation;
    }
}
