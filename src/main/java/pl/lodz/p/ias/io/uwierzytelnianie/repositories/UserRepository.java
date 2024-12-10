package pl.lodz.p.ias.io.uwierzytelnianie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;

@Repository
public interface UserRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
}
