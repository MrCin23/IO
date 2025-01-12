package pl.lodz.p.ias.io.darczyncy.dto.output;


import pl.lodz.p.ias.io.darczyncy.model.Donation;

public record FinancialDonationOutputDTO(
        long id,
        Long donorId,
        Long needId,
        long warehouseId,
        double amount,
        double calculatedVAT,
        String currency
) { }
