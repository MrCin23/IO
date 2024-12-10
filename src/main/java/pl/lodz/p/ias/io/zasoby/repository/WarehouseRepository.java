package pl.lodz.p.ias.io.zasoby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.zasoby.model.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    //Warehouse findById(Long id);
}