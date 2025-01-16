package pl.lodz.p.ias.io.darczyncy.dto.output;

import pl.lodz.p.ias.io.zasoby.utils.ResourceStatus;
import java.time.LocalDate;

/**
 * DTO reprezentujące dane darowizny rzeczowej.
 * Zawiera szczegóły dotyczące darowizny rzeczowej, w tym kategorię, ilość zasobów oraz status.
 */
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
