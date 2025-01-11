package pl.lodz.p.ias.io.zasoby.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ias.io.zasoby.dto.ResourceDTO;
import pl.lodz.p.ias.io.zasoby.dto.WarehouseDTO;
import pl.lodz.p.ias.io.zasoby.model.Warehouse;
import pl.lodz.p.ias.io.zasoby.repository.WarehouseRepository;
import pl.lodz.p.ias.io.zasoby.utils.WarehouseConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseConverter warehouseConverter = new WarehouseConverter();

//    @Autowired
//    public WarehouseService(WarehouseRepository warehouseRepository) {
//        this.warehouseRepository = warehouseRepository;
//    }

    public WarehouseDTO addWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = warehouseConverter.convertDTOToWarehouse(warehouseDTO);
        warehouseRepository.save(warehouse);
        return warehouseConverter.convertWarehouseToDTO(warehouse);
    }

    public WarehouseDTO getWarehouseById(long id) {
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
    public void updateWarehouse(long id, WarehouseDTO warehouseDTO) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with id " + id + " not found"));

        warehouse.setWarehouseName(warehouseDTO.getWarehouseName());
        warehouse.setLocation(warehouseDTO.getLocation());
    }

//    public WarehouseDTO findById(Long id) {
//        Warehouse warehouse = warehouseRepository.findById(id);
//        return new WarehouseDTO(warehouse.getWarehouseName(),
//                warehouse.getLocation());
//    }
}