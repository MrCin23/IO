package pl.lodz.p.ias.io.powiadomienia.notification;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.powiadomienia.Interfaces.INotificationService;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.AccountRepository;

import java.util.List;

/**
 * Serwis odpowiedzialny za zarządzanie powiadomieniami.
 */
@Service
@AllArgsConstructor
public class NotificationService implements INotificationService {
    NotificationRepository notificationRepository;
    AccountRepository accountRepository;

    /**
     * Pobiera listę powiadomień przypisanych do użytkownika o podanym ID.
     *
     * @param username nazwa użytkownika, dla którego mają zostać pobrane powiadomienia
     * @return lista obiektów {@link Notification} przypisanych do użytkownika
     */
    public List<Notification> getNotifications(String username) {
        return notificationRepository.findAllByUser(accountRepository.findByUsername(username));
    }

    /**
     * Zapisuje nowe powiadomienie w bazie danych.
     *
     * @param notification obiekt {@link Notification} do zapisania
     * @return zapisany obiekt {@link Notification}
     */
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    /**
     * Tworzy nowe powiadomienie na podstawie danych z obiektu DTO i zapisuje je w bazie danych.
     *
     * @param notification obiekt {@link NotificationDto} zawierający dane nowego powiadomienia
     * @return zapisany obiekt {@link Notification}
     */
    public Notification saveFromDto(NotificationDto notification) {
        Notification notificationFromDto = new Notification();
        notificationFromDto.setMessage(notification.getMessage());
        notificationFromDto.setType(notification.getType());
        notificationFromDto.setUser(accountRepository.findById(notification.getUserId()).get());
        return notificationRepository.save(notificationFromDto);
    }

    /**
     * Usuwa powiadomienie o podanym ID z bazy danych.
     *
     * @param id identyfikator powiadomienia do usunięcia
     */
    public void hide(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if(notificationRepository.findById(id).get().getUser().getUsername().equals(username)) {
            notificationRepository.deleteById(id);
        }
    }

    /**
     * Wysyła powiadomienie z podaną wiadomością, typem i użytkownikiem.
     *
     * @param message treść powiadomienia
     * @param type typ powiadomienia ({@link NotificationType})
     * @param user użytkownik ({@link Account}), do którego powiadomienie ma zostać wysłane
     * @return utworzony obiekt {@link Notification}
     */
    @Override
    public Notification notify(String message, NotificationType type, Account user) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setType(type);
        notification.setUser(user);
        return notificationRepository.save(notification);
    }

    public void readAllNotifications(String username) {
        List<Notification> notifications = notificationRepository.getNotificationByUser_Username(username);
        for (Notification notification : notifications){
            notification.setRead(true);
        }
        notificationRepository.saveAll(notifications);
    }
}
