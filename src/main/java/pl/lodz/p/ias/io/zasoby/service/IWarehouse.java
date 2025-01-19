package pl.lodz.p.ias.io.zasoby.service;

import pl.lodz.p.ias.io.zasoby.dto.WarehouseDTO;

import java.util.List;

public interface IWarehouse {

    WarehouseDTO addWarehouse(WarehouseDTO warehouseDTO);

    WarehouseDTO getWarehouseById(Long id);

    List<WarehouseDTO> getAllWarehouses();

    void updateWarehouse(Long id, WarehouseDTO warehouseDTO);

    WarehouseDTO getWarehouseByMapPoint(Long mapPointId);
}
