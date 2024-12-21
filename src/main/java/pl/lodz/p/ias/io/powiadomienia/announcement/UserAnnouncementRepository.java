package pl.lodz.p.ias.io.powiadomienia.announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;

import java.util.List;

@Repository
public interface UserAnnouncementRepository extends JpaRepository<UserAnnouncement, Long> {

    List<UserAnnouncement> findAllByUser(Account account);

}
