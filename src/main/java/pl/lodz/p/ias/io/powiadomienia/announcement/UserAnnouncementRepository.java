package pl.lodz.p.ias.io.powiadomienia.announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;

import java.util.List;

/**
 * Repozytorium ogłoszeń użytkownika.
 */
@Repository
public interface UserAnnouncementRepository extends JpaRepository<UserAnnouncement, Long> {

    /**
     * Znajduje wszystkie powiązania ogłoszeń dla podanego użytkownika.
     *
     * @param account obiekt {@link Account} reprezentujący użytkownika
     * @return lista obiektów {@link UserAnnouncement} powiązanych z użytkownikiem
     */
    List<UserAnnouncement> findAllByUser(Account account);

}
