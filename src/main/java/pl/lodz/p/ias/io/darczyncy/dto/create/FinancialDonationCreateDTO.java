package pl.lodz.p.ias.io.darczyncy.dto.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public record FinancialDonationCreateDTO (
        @NotNull
        Long needId,

        @NotNull
        @Min(1)
        double amount,

        @NotNull
        String currency
){}
