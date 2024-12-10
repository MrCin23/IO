package pl.lodz.p.ias.io.darczyncy.utils;

import pl.lodz.p.ias.io.darczyncy.dto.output.FinancialDonationOutputDTO;
import pl.lodz.p.ias.io.darczyncy.dto.output.ItemDonationOutputDTO;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;

public class DonationMapper {

    public static FinancialDonationOutputDTO toFinancialDonationOutputDTO(FinancialDonation financialDonation) {
        return new FinancialDonationOutputDTO(
                financialDonation.getId(),
                financialDonation.getDonor(),
                financialDonation.getNeed(),
                financialDonation.getWarehouseId(),
                financialDonation.getAmount(),
                financialDonation.getCalculatedVAT(),
                financialDonation.getCurrency()
        );
    }

    public static ItemDonationOutputDTO toItemDonationOutputDTO(ItemDonation itemDonation) {
        return new ItemDonationOutputDTO(
                itemDonation.getId(),
                itemDonation.getDonor(),
                itemDonation.getNeed(),
                itemDonation.getResourceName(),
                itemDonation.getResourceType(),
                itemDonation.getDescription(),
                itemDonation.getResourceQuantity(),
                itemDonation.getWarehouseId()
        );
    }
}
