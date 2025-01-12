package pl.lodz.p.ias.io.zasoby.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseDTO {
    private UUID warehouseId;
    private String warehouseName;
    private String location;

    public WarehouseDTO(UUID warehouseId, String warehouseName, String location) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.location = location;
    }
}