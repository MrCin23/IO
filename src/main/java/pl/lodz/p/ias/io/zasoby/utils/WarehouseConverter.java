package pl.lodz.p.ias.io.zasoby.utils;

import pl.lodz.p.ias.io.zasoby.dto.WarehouseDTO;
import pl.lodz.p.ias.io.zasoby.model.Warehouse;

public class WarehouseConverter {
    public WarehouseDTO convertWarehouseToDTO(Warehouse warehouse) {
        return new WarehouseDTO(
                warehouse.getWarehouseName(),
                warehouse.getLocation()
        );
    }

    public Warehouse convertDTOToWarehouse(WarehouseDTO warehouseDTO) {
        return new Warehouse(
                warehouseDTO.getWarehouseName(),
                warehouseDTO.getLocation()
        );
    }
}