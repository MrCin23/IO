package pl.lodz.p.ias.io.zasoby.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseDTO {
    private String warehouseName;
    private String location;

    public WarehouseDTO(String warehouseName, String location) {
        this.warehouseName = warehouseName;
        this.location = location;
    }
}