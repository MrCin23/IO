package pl.lodz.p.ias.io.darczyncy.mappers;

import pl.lodz.p.ias.io.darczyncy.dto.output.FinancialDonationOutputDTO;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;

public class FinancialDonationMapper {

    public static FinancialDonationOutputDTO toOutputDTO(FinancialDonation financialDonation) {
        return new FinancialDonationOutputDTO(
                financialDonation.getId(),
                financialDonation.getDonor().getId(),
                financialDonation.getNeed().getId(),
                financialDonation.getWarehouseId(),
                financialDonation.getAmount(),
                financialDonation.getCurrency().toString()
        );
    }
}
