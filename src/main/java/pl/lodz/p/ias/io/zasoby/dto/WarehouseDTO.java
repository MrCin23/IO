package pl.lodz.p.ias.io.zasoby.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseDTO {
    private String warehouseName;
    private String location;

    public WarehouseDTO(String warehouseName, String location, String organisationName) {
        this.warehouseName = warehouseName;
        this.location = location;
    }
}
