package pl.lodz.p.ias.io.powiadomienia.notification;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Kontroler REST do zarządzania powiadomieniami.
 */
@CrossOrigin("http://localhost:5173")
@RestController
@AllArgsConstructor
public class NotificationController {

    NotificationService notificationService;

    /**
     * Pobiera listę powiadomień dla użytkownika o podanym ID.
     *
     * @param id identyfikator użytkownika, dla którego mają zostać pobrane powiadomienia
     * @return lista obiektów {@link Notification} przypisanych do użytkownika
     */
    @GetMapping("/notifications/user/{id}")
    public List<Notification> getNotifications(@PathVariable Long id) {
        return notificationService.getNotifications(id);
    }

    /**
     * Dodaje nowe powiadomienie na podstawie danych przekazanych w obiekcie DTO.
     *
     * @param notification obiekt {@link NotificationDto} zawierający dane nowego powiadomienia
     * @return utworzony obiekt {@link Notification}
     */
    @PostMapping("/notifications")
    public Notification addNotification(@RequestBody @Valid NotificationDto notification) {
        return notificationService.saveFromDto(notification);
    }

    /**
     * Ukrywa powiadomienie o podanym ID.
     *
     * @param id identyfikator powiadomienia do ukrycia
     */
    @PostMapping("/notifications/{id}/hide")
    public void hideNotification(@PathVariable Long id) {
        notificationService.hide(id);
    }
}
