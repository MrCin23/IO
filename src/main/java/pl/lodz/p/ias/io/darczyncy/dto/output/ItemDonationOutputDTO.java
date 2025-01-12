package pl.lodz.p.ias.io.darczyncy.dto.output;

import pl.lodz.p.ias.io.darczyncy.model.Donation;

public record ItemDonationOutputDTO(
        long id,
        Long donorId,
        Long needId,
        String itemName,
        String category,
        String description,
        int resourceQuantity,
        long warehouseId,
        Donation.AcceptanceStatus acceptanceStatus
) { }
