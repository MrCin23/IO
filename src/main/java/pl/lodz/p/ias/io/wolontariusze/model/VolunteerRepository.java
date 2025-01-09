package pl.lodz.p.ias.io.wolontariusze.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Role;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Users;

import java.util.Set;

@Repository
public interface VolunteerRepository extends JpaRepository<Users, Long> {
//    Set<Users> findAllByUser_ids(Set<Long> ids);
    Set<Users> findAllByUserIdInAndRole(Set<Long> ids, Role role);
}
