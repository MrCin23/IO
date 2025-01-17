package pl.lodz.p.ias.io.poszkodowani.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;

import java.util.List;

@Repository
public interface MaterialNeedRepository extends JpaRepository<MaterialNeed, Long> {
    List<MaterialNeed> findByUserId(Long userId);
}
