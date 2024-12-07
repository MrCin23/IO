package pl.lodz.p.ias.io.powiadomienia.mock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MockUserRepo extends JpaRepository<MockUser, Long> {



}
