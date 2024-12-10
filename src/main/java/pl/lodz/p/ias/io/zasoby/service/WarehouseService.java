package pl.lodz.p.ias.io.zasoby.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.zasoby.dto.WarehouseDTO;
import pl.lodz.p.ias.io.zasoby.model.Warehouse;
import pl.lodz.p.ias.io.zasoby.repository.WarehouseRepository;
import pl.lodz.p.ias.io.zasoby.utils.WarehouseConverter;

import java.util.List;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseConverter warehouseConverter = new WarehouseConverter();

    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public WarehouseDTO addWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = warehouseConverter.convertDTOToWarehouse(warehouseDTO);
        warehouseRepository.save(warehouse);
        return warehouseConverter.convertWarehouseToDTO(warehouse);
    }

    public List<WarehouseDTO> findAll() {
        return warehouseRepository.findAll().stream()
                .map(warehouse -> new WarehouseDTO(
                        warehouse.getWarehouseName(),
                        warehouse.getLocation()
                ))
                .toList();
    }

//    public WarehouseDTO findById(Long id) {
//        Warehouse warehouse = warehouseRepository.findById(id);
//        return new WarehouseDTO(warehouse.getWarehouseName(),
//                warehouse.getLocation());
//    }
}