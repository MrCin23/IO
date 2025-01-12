package pl.lodz.p.ias.io.powiadomienia.notification;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
public class NotificationController {

    NotificationService notificationService;

    @GetMapping("/notifications/user/{id}")
    public List<Notification> getNotifications(@PathVariable Long id) {
        return notificationService.getNotifications(id);
    }

    @PostMapping("/notifications")
    public Notification addNotification(@RequestBody @Valid NotificationDto notification) {
        return notificationService.saveFromDto(notification);
    }

    @PostMapping("/notifications/{id}/hide")
    public void hideNotification(@PathVariable Long id) {
        notificationService.hide(id);
    }
}
