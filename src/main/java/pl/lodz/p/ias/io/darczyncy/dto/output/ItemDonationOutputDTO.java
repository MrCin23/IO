package pl.lodz.p.ias.io.darczyncy.dto.output;

import pl.lodz.p.ias.io.darczyncy.model.Donation;

import java.time.LocalDate;

public record ItemDonationOutputDTO(
        long id,
        Long donorId,
        String itemName,
        String category,
        String resourceType,
        String description,
        int resourceQuantity,
        long warehouseId,
        LocalDate donationDate,
        Donation.AcceptanceStatus acceptanceStatus,
        String needDescription
) { }
