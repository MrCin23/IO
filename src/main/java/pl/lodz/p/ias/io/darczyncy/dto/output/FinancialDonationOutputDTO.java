package pl.lodz.p.ias.io.darczyncy.dto.output;


import pl.lodz.p.ias.io.darczyncy.model.Donation;

import java.time.LocalDate;

public record FinancialDonationOutputDTO(
        long id,
        Long donorId,
        long warehouseId,
        double amount,
        String currency,
        LocalDate date,
        Donation.AcceptanceStatus acceptanceStatus,
        String needDescription
) { }
