package pl.lodz.p.ias.io.zasoby.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('PRZEDSTAWICIEL_WŁADZ', 'ORGANIZACJA_POMOCOWA')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WarehouseDTO addWarehouse(@RequestBody @Valid WarehouseDTO warehouseDTO) {
        return warehouseService.addWarehouse(warehouseDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WarehouseDTO getWarehouse(@PathVariable Long id) {
        return warehouseService.getWarehouseById(id);
    }

    @PreAuthorize("hasAnyRole('PRZEDSTAWICIEL_WŁADZ', 'ORGANIZACJA_POMOCOWA', 'DARCZYŃCA')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WarehouseDTO> getAllWarehouses() {
        return warehouseService.getAllWarehouses();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateWarehouse(@PathVariable Long id, @RequestBody @Valid WarehouseDTO warehouseDTO) {
        warehouseService.updateWarehouse(id, warehouseDTO);
    }

    @GetMapping("point/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WarehouseDTO getWarehouseByMapPointId(@PathVariable Long id) {
        return warehouseService.getWarehouseByMapPoint(id);
    }
}