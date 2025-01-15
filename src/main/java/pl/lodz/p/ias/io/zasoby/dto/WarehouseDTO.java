package pl.lodz.p.ias.io.zasoby.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class WarehouseDTO {
    private Long warehouseId;
    private String warehouseName;
    private String location;

    public WarehouseDTO(Long warehouseId, String warehouseName, String location) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.location = location;
    }
}