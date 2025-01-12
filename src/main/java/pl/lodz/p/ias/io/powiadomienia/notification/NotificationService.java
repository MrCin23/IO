package pl.lodz.p.ias.io.powiadomienia.notification;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.powiadomienia.Interfaces.INotificationService;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.UserRepository;

import java.util.List;

/**
 * Serwis odpowiedzialny za zarządzanie powiadomieniami.
 */
@Service
@AllArgsConstructor
public class NotificationService implements INotificationService {
    NotificationRepository notificationRepository;
    UserRepository userRepository;

    /**
     * Pobiera listę powiadomień przypisanych do użytkownika o podanym ID.
     *
     * @param userId identyfikator użytkownika, dla którego mają zostać pobrane powiadomienia
     * @return lista obiektów {@link Notification} przypisanych do użytkownika
     */
    public List<Notification> getNotifications(Long userId) {
        return notificationRepository.findAllByUser(userRepository.findById(userId).get());
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
        notificationFromDto.setUser(userRepository.findById(notification.getUserId()).get());
        return notificationRepository.save(notificationFromDto);
    }

    /**
     * Usuwa powiadomienie o podanym ID z bazy danych.
     *
     * @param id identyfikator powiadomienia do usunięcia
     */
    public void hide(Long id) {
        notificationRepository.deleteById(id);
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
}
