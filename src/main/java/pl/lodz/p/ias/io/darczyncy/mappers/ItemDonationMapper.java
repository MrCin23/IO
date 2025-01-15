package pl.lodz.p.ias.io.darczyncy.mappers;

import pl.lodz.p.ias.io.darczyncy.dto.output.ItemDonationOutputDTO;
import pl.lodz.p.ias.io.darczyncy.dto.update.ItemDonationUpdateDTO;
import pl.lodz.p.ias.io.darczyncy.model.Donation;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;

public class ItemDonationMapper {

    public static ItemDonationOutputDTO toOutputDTO(ItemDonation itemDonation, MaterialNeed materialNeed) {
        return new ItemDonationOutputDTO(
                itemDonation.getId(),
                itemDonation.getDonor().getId(),
                itemDonation.getResourceName(),
                itemDonation.getCategory().toString(),
                itemDonation.getResourceType(),
                itemDonation.getDescription(),
                itemDonation.getResourceQuantity(),
                itemDonation.getWarehouseId(),
                itemDonation.getDonationDate(),
                itemDonation.getResourceStatus(),
                materialNeed.getDescription()
        );
    }


    public static ItemDonation fromUpdateDTO(ItemDonationUpdateDTO itemDonationUpdateDTO) {
        return ItemDonation.builder()
                .resourceName(itemDonationUpdateDTO.name())
                .description(itemDonationUpdateDTO.description())
                .resourceStatus(itemDonationUpdateDTO.resourceStatus())
                .category(itemDonationUpdateDTO.category())
                .build();
    }
}
