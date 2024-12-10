package pl.lodz.p.ias.io.zasoby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.zasoby.model.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    void create(Resource resource);
}