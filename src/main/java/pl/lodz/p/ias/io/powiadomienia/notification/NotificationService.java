package pl.lodz.p.ias.io.powiadomienia.notification;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.powiadomienia.Interfaces.INotificationService;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService implements INotificationService {
    NotificationRepository notificationRepository;
    UserRepository userRepository;

    public List<Notification> getNotifications(Long userId) {
        return notificationRepository.findAllByUser(userRepository.findById(userId).get());
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Notification saveFromDto(NotificationDto notification) {
        Notification notificationFromDto = new Notification();
        notificationFromDto.setMessage(notification.getMessage());
        notificationFromDto.setType(notification.getType());
        notificationFromDto.setUser(userRepository.findById(notification.getUserId()).get());
        return notificationRepository.save(notificationFromDto);
    }

    public void hide(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public Notification notify(String message, NotificationType type, Account user) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setType(type);
        notification.setUser(user);
        return notificationRepository.save(notification);
    }
}
