package pl.lodz.p.ias.io.darczyncy.dto.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;

/**
 * DTO do tworzenia darowizny rzeczowej.
 * Zawiera dane dotyczące darowizny rzeczowej, w tym nazwę, kategorię, opis i ilość zasobów.
 */
public record ItemDonationCreateDTO (

        @NotNull
        String name,

        @NotNull
        Long needId,

        @NotNull
        String category,

        String description,

        @NotNull
        @Min(1)
        int resourceQuantity
){}
