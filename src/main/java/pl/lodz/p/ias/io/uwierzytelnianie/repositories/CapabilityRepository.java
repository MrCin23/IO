package pl.lodz.p.ias.io.uwierzytelnianie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Capability;

public interface CapabilityRepository extends JpaRepository<Capability, Long> {
}
