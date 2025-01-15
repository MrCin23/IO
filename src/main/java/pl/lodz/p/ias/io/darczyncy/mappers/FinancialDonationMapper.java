package pl.lodz.p.ias.io.darczyncy.mappers;

import lombok.RequiredArgsConstructor;
import pl.lodz.p.ias.io.darczyncy.dto.output.FinancialDonationOutputDTO;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.poszkodowani.repository.FinancialNeedRepository;

public class FinancialDonationMapper {

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
