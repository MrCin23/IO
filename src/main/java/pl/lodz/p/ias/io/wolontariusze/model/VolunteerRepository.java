package pl.lodz.p.ias.io.wolontariusze.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Role;

import java.util.List;
import java.util.Set;

@Repository
public interface VolunteerRepository extends JpaRepository<Account, Long> {
    Set<Account> findAllByIdInAndRole(Set<Long> ids, Role role);
    List<Account> findAllByRole(Role role);
}
