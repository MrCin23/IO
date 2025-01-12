package pl.lodz.p.ias.io.darczyncy.mappers;

import pl.lodz.p.ias.io.darczyncy.dto.output.ItemDonationOutputDTO;
import pl.lodz.p.ias.io.darczyncy.dto.update.ItemDonationUpdateDTO;
import pl.lodz.p.ias.io.darczyncy.model.Donation;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;

public class ItemDonationMapper {

    public static ItemDonationOutputDTO toOutputDTO(ItemDonation itemDonation) {
        return new ItemDonationOutputDTO(
                itemDonation.getId(),
                itemDonation.getDonor().getId(),
                itemDonation.getNeed().getId(),
                itemDonation.getResourceName(),
                itemDonation.getResourceType(),
                itemDonation.getDescription(),
                itemDonation.getResourceQuantity(),
                itemDonation.getWarehouseId(),
                itemDonation.getAcceptanceStatus()
        );
    }

    public static ItemDonation fromUpdateDTO(ItemDonationUpdateDTO itemDonationUpdateDTO) {
        return ItemDonation.builder()
                .resourceName(itemDonationUpdateDTO.name())
                .description(itemDonationUpdateDTO.description())
                .acceptanceStatus(itemDonationUpdateDTO.acceptanceStatus())
                .category(itemDonationUpdateDTO.category())
                .build();
    }
}
