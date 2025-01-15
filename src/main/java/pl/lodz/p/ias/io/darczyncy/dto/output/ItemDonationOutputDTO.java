package pl.lodz.p.ias.io.darczyncy.dto.output;

import pl.lodz.p.ias.io.darczyncy.model.Donation;
import pl.lodz.p.ias.io.zasoby.utils.ResourceStatus;

import java.time.LocalDate;

public record ItemDonationOutputDTO(
        long id,
        Long donorId,
        String itemName,
        String category,
        String resourceType,
        String description,
        int resourceQuantity,
        Long warehouseId,
        LocalDate donationDate,
        ResourceStatus resourceStatus,
        String needDescription
) { }
