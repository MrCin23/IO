//package pl.lodz.p.ias.io.zasoby;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import pl.lodz.p.ias.io.zasoby.dto.WarehouseDTO;
//import pl.lodz.p.ias.io.zasoby.model.Warehouse;
//import pl.lodz.p.ias.io.zasoby.repository.WarehouseRepository;
//import pl.lodz.p.ias.io.zasoby.service.WarehouseService;
//
//import java.util.List;
//
//@ExtendWith(MockitoExtension.class)
//public class WarehouseTest {
//    @Mock
//    private WarehouseRepository warehouseRepository;
//
//    private WarehouseService warehouseService;
//
//    @BeforeEach
//    void setUp() {
//        warehouseService = new WarehouseService(warehouseRepository);
//    }
//
//    @Test
//    public void addWarehouseTest() {
//        WarehouseDTO dto = new WarehouseDTO("magazyn", "lodz");
//        Warehouse warehouse = new Warehouse();
//        warehouse.setWarehouseName(dto.getWarehouseName());
//        warehouse.setLocation(dto.getLocation());
//
//        Mockito.when(warehouseRepository.save(Mockito.any(Warehouse.class))).thenReturn(warehouse);
//
//        WarehouseDTO warehouseDTO = warehouseService.addWarehouse(dto);
//        Assertions.assertEquals(dto.getWarehouseName(), warehouseDTO.getWarehouseName());
//    }
//
//    @Test
//    public void testFindAllWarehousesTest() {
//        Warehouse warehouse = new Warehouse();
//        warehouse.setWarehouseName("Warehouse 1");
//        warehouse.setLocation("Location 1");
//
//        Mockito.when(warehouseRepository.findAll()).thenReturn(List.of(warehouse));
//
//        List<WarehouseDTO> result = warehouseService.getAllWarehouses();
//
//        Assertions.assertEquals(1, result.size());
//        Assertions.assertEquals("Warehouse 1", result.get(0).getWarehouseName());
//    }
//}
