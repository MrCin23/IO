package pl.lodz.p.ias.io.darczyncy.dto.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import pl.lodz.p.ias.io.darczyncy.model.Donation;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;

public record FinancialDonationCreateDTO (
        @NotNull
        Donation.User donor,

        @NotNull
        Donation.Need need,

        @NotNull
        long warehouseId,

        @NotNull
        @Min(1)
        double amount,

        double calculatedVAT,

        @NotNull
        FinancialDonation.Currency currency
){}
