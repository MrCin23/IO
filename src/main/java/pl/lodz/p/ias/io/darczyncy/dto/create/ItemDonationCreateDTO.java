package pl.lodz.p.ias.io.darczyncy.dto.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import pl.lodz.p.ias.io.darczyncy.model.Donation;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;

public record ItemDonationCreateDTO (
        @NotNull
        Donation.User donor,

        @NotNull
        Donation.Need need,

        @NotNull
        String itemName,

        @NotNull
        ItemDonation.ItemCategory category,

        String description,

        @NotNull
        @Min(1)
        int resourceQuantity,

        @NotNull
        long warehouseId
){}
