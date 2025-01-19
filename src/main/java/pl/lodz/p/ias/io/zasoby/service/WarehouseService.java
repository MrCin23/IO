package pl.lodz.p.ias.io.zasoby.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ias.io.mapy.model.MapPoint;
import pl.lodz.p.ias.io.mapy.repository.MapPointRepository;
import pl.lodz.p.ias.io.zasoby.dto.WarehouseDTO;
import pl.lodz.p.ias.io.zasoby.model.Warehouse;
import pl.lodz.p.ias.io.zasoby.repository.WarehouseRepository;
import pl.lodz.p.ias.io.zasoby.utils.WarehouseConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WarehouseService implements IWarehouse{
    private final WarehouseRepository warehouseRepository;
    private final WarehouseConverter warehouseConverter = new WarehouseConverter();
    private MapPointRepository mapPointRepository;


    public WarehouseDTO addWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = warehouseConverter.convertDTOToWarehouse(warehouseDTO);
        warehouseRepository.save(warehouse);
        return warehouseConverter.convertWarehouseToDTO(warehouse);
    }

    public WarehouseDTO getWarehouseById(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with id " + id + " not found"));
        return warehouseConverter.convertWarehouseToDTO(warehouse);
    }

    public List<WarehouseDTO> getAllWarehouses() {
        return warehouseRepository.findAll()
                .stream()
                .map(warehouseConverter::convertWarehouseToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateWarehouse(Long id, WarehouseDTO warehouseDTO) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with id " + id + " not found"));

        warehouse.setWarehouseName(warehouseDTO.getWarehouseName());
        warehouse.setLocation(warehouseDTO.getLocation());
        warehouse.setMapPoint(warehouseDTO.getMapPoint());
        warehouseRepository.save(warehouse);
    }

    public WarehouseDTO getWarehouseByMapPoint(Long mapPointId) {
        MapPoint mapPoint = mapPointRepository.findById(mapPointId)
                .orElseThrow(() -> new IllegalArgumentException("MapPoint with id " + mapPointId + " not found"));

        Warehouse warehouse = warehouseRepository.findByMapPoint(mapPoint)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with MapPoint id " + mapPointId + " not found"));

        return warehouseConverter.convertWarehouseToDTO(warehouse);
    }
}