package pl.lodz.p.ias.io.darczyncy.dto.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemDonationCreateDTO (
        @NotNull
        Long donorId,

        @NotNull
        Long needId,

        @NotNull
        String itemName,

        @NotNull
        String category,

        String description,

        @NotNull
        @Min(1)
        int resourceQuantity,

        @NotNull
        long warehouseId
){}
