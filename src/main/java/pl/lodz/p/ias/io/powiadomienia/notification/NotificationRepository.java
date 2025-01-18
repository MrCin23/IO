package pl.lodz.p.ias.io.powiadomienia.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;

import java.util.List;
/**
 * Repozytorium powiadomień.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Znajduje wszystkie powiadomienia przypisane do danego użytkownika.
     *
     * @param user {@link Account} użytkownik, dla którego mają zostać pobrane powiadomienia
     * @return lista obiektów {@link Notification} przypisanych do użytkownika
     */
    List<Notification> findAllByUser(Account user);

    List<Notification> getNotificationByUser_Username(String username);
}
