package pl.lodz.p.ias.io.zasoby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.ias.io.zasoby.model.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
    Warehouse findById(long id);
}
