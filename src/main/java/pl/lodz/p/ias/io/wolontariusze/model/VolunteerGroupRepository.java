package pl.lodz.p.ias.io.wolontariusze.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerGroupRepository extends JpaRepository<VolunteerGroup, Long> {
    boolean existsByName(String name);
}
