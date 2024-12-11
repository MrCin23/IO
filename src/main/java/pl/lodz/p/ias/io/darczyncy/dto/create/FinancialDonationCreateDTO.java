package pl.lodz.p.ias.io.darczyncy.dto.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public record FinancialDonationCreateDTO (
        @NotNull
        Long donorId,

        @NotNull
        Long needId,

        @NotNull
        long warehouseId,

        @NotNull
        @Min(1)
        double amount,

        double calculatedVAT,

        @NotNull
        String currency
){}
