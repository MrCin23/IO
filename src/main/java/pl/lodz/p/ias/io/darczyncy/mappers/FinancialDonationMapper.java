package pl.lodz.p.ias.io.darczyncy.mappers;

import pl.lodz.p.ias.io.darczyncy.dto.output.FinancialDonationOutputDTO;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;

/**
 * Klasa mapująca obiekty związane z darowiznami finansowymi do obiektów DTO (Data Transfer Object).
 */
public class FinancialDonationMapper {

    /**
     * Mapuje obiekt darowizny finansowej na obiekt DTO.
     *
     * @param financialDonation Obiekt darowizny finansowej, który ma zostać przekonwertowany.
     * @param financialNeed Obiekt opisujący potrzebę finansową, który zostanie użyty do wypełnienia opisu w DTO.
     * @return Obiekt DTO zawierający dane darowizny finansowej.
     */
    public static FinancialDonationOutputDTO toOutputDTO(FinancialDonation financialDonation, FinancialNeed financialNeed) {
        return new FinancialDonationOutputDTO(
                financialDonation.getId(),
                financialDonation.getDonor().getId(),
                financialDonation.getWarehouseId(),
                financialDonation.getAmount(),
                financialDonation.getCurrency().toString(),
                financialDonation.getDonationDate(),
                financialDonation.getResourceStatus(),
                financialNeed.getDescription()
        );
    }
}
