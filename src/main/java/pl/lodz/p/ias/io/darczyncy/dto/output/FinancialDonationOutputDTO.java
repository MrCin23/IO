package pl.lodz.p.ias.io.darczyncy.dto.output;


import pl.lodz.p.ias.io.darczyncy.model.Donation;
import pl.lodz.p.ias.io.zasoby.utils.ResourceStatus;

import java.time.LocalDate;

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
