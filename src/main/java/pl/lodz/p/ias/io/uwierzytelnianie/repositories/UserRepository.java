package pl.lodz.p.ias.io.uwierzytelnianie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
}
