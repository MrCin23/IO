package pl.lodz.p.ias.io.darczyncy.dto.output;


public record FinancialDonationOutputDTO(
        long id,
        Long donorId,
        Long needId,
        long warehouseId,
        double amount,
        double calculatedVAT,
        String currency
) { }
