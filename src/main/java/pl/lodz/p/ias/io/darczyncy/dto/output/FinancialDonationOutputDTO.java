package pl.lodz.p.ias.io.darczyncy.dto.output;

import pl.lodz.p.ias.io.darczyncy.model.Donation;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;

public record FinancialDonationOutputDTO(
        long id,
        Donation.User donor,
        Donation.Need need,
        long warehouseId,
        double amount,
        double calculatedVAT,
        FinancialDonation.Currency currency
) { }
