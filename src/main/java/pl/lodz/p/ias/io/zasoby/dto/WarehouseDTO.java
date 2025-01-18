package pl.lodz.p.ias.io.zasoby.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.ias.io.mapy.model.MapPoint;


@Getter
@Setter
@NoArgsConstructor
public class WarehouseDTO {
    private Long warehouseId;
    private String warehouseName;
    private String location;
    private MapPoint mapPoint;

    public WarehouseDTO(Long warehouseId, String warehouseName, String location, MapPoint mapPoint) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.location = location;
        this.mapPoint = mapPoint;
    }
}