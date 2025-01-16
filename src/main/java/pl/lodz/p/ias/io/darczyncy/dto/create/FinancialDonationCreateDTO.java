package pl.lodz.p.ias.io.darczyncy.dto.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO do tworzenia darowizny finansowej.
 * Zawiera dane dotyczące darowizny finansowej, w tym kwotę, walutę oraz identyfikator potrzeby.
 */
public record FinancialDonationCreateDTO (
        @NotNull
        Long needId,

        @NotNull
        @Min(1)
        double amount,

        @NotNull
        String currency
){}
