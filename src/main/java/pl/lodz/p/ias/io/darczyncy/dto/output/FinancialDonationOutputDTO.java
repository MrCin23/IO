package pl.lodz.p.ias.io.darczyncy.dto.output;

import pl.lodz.p.ias.io.zasoby.utils.ResourceStatus;
import java.time.LocalDate;

/**
 * DTO reprezentujące dane darowizny finansowej.
 * Zawiera szczegóły dotyczące darowizny finansowej, w tym datę, kwotę i status zasobów.
 */
public record FinancialDonationOutputDTO(
        long id,
        Long donorId,
        Long warehouseId,
        double amount,
        String currency,
        LocalDate donationDate,
        ResourceStatus resourceStatus,
        String needDescription
) { }
