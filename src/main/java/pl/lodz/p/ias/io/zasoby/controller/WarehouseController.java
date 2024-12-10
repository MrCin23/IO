package pl.lodz.p.ias.io.zasoby.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.zasoby.dto.WarehouseDTO;
import pl.lodz.p.ias.io.zasoby.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping("/")
    public ResponseEntity<WarehouseDTO> addWarehouse(@RequestBody WarehouseDTO warehouseDTO) {
        WarehouseDTO warehouse = warehouseService.addWarehouse(warehouseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(warehouse);
    }

    @GetMapping("/getAllWarehouses")
    public ResponseEntity<List<WarehouseDTO>> getAllWarehouses() {
        List<WarehouseDTO> warehouseDTOs = warehouseService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(warehouseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDTO> getWarehouseById(@PathVariable("id") String id) {
        long warehouseId = Long.parseLong(id);
        WarehouseDTO warehouseDTO = warehouseService.findById(warehouseId);
        return ResponseEntity.status(HttpStatus.OK).body(warehouseDTO);
    }
}
