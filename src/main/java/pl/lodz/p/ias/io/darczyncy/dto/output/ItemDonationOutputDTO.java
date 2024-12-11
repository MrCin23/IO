package pl.lodz.p.ias.io.darczyncy.dto.output;

public record ItemDonationOutputDTO(
        long id,
        Long donorId,
        Long needId,
        String itemName,
        String category,
        String description,
        int resourceQuantity,
        long warehouseId
) { }
