package pl.lodz.p.ias.io.darczyncy.dto.output;

import pl.lodz.p.ias.io.darczyncy.model.Donation;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;

public record ItemDonationOutputDTO(
        long id,
        Donation.User donor,
        Donation.Need need,
        String itemName,
        String category,
        String description,
        int resourceQuantity,
        long warehouseId
) { }
