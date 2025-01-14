package pl.lodz.p.ias.io.powiadomienia.notification;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
     * Pobiera listę powiadomień dla uwierzytelnionego użytkownika.
     *
     * @return lista obiektów {@link Notification} przypisanych do użytkownika
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/notifications/user")
    public List<Notification> getNotifications() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return notificationService.getNotifications(username);
    }

    /**
     * Dodaje nowe powiadomienie na podstawie danych przekazanych w obiekcie DTO.
     *
     * @param notification obiekt {@link NotificationDto} zawierający dane nowego powiadomienia
     * @return utworzony obiekt {@link Notification}
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/notifications")
    public Notification addNotification(@RequestBody @Valid NotificationDto notification) {
        return notificationService.saveFromDto(notification);
    }

    /**
     * Ukrywa powiadomienie o podanym ID.
     *
     * @param id identyfikator powiadomienia do ukrycia
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/notifications/{id}/hide")
    public void hideNotification(@PathVariable Long id) {
        notificationService.hide(id);
    }
}
