package pl.lodz.p.ias.io.darczyncy.mappers;

import pl.lodz.p.ias.io.darczyncy.dto.output.ItemDonationOutputDTO;
import pl.lodz.p.ias.io.darczyncy.dto.update.ItemDonationUpdateDTO;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;

/**
 * Klasa mapująca obiekty związane z darowiznami rzeczowymi do obiektów DTO (Data Transfer Object) oraz umożliwiająca aktualizację darowizn.
 */
public class ItemDonationMapper {

    /**
     * Mapuje obiekt darowizny rzeczowej na obiekt DTO.
     *
     * @param itemDonation Obiekt darowizny rzeczowej, który ma zostać przekonwertowany.
     * @param materialNeed Obiekt opisujący potrzebę materialną, który zostanie użyty do wypełnienia opisu w DTO.
     * @return Obiekt DTO zawierający dane darowizny rzeczowej.
     */
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

    /**
     * Mapuje obiekt DTO zaktualizowanej darowizny rzeczowej na obiekt darowizny rzeczowej.
     *
     * @param itemDonationUpdateDTO Obiekt DTO zawierający dane do zaktualizowania darowizny rzeczowej.
     * @return Obiekt darowizny rzeczowej zaktualizowany danymi z DTO.
     */
    public static ItemDonation fromUpdateDTO(ItemDonationUpdateDTO itemDonationUpdateDTO) {
        return ItemDonation.builder()
                .resourceName(itemDonationUpdateDTO.name())
                .description(itemDonationUpdateDTO.description())
                .resourceStatus(itemDonationUpdateDTO.resourceStatus())
                .category(itemDonationUpdateDTO.category())
                .build();
    }
}
