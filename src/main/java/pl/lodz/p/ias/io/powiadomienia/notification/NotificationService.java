package pl.lodz.p.ias.io.powiadomienia.notification;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.powiadomienia.Interfaces.INotificationService;
import pl.lodz.p.ias.io.powiadomienia.mock.MockUser;
import pl.lodz.p.ias.io.powiadomienia.mock.MockUserRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService implements INotificationService {
    NotificationRepository notificationRepository;
    MockUserRepo mockUserRepo;

    public List<Notification> getNotifications(Long userId) {
        return notificationRepository.findAllByUser_Id(userId);
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }



    public Notification saveFromDto(NotificationDto notification) {
        Notification notificationFromDto = new Notification();
        notificationFromDto.setMessage(notification.getMessage());
        notificationFromDto.setType(notification.getType());
        notificationFromDto.setUser(mockUserRepo.findById(notification.getUserId()).get());
        return notificationRepository.save(notificationFromDto);
    }

    public void hide(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public Notification notify(String message, NotificationType type, MockUser user) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setType(type);
        notification.setUser(user);
        return notificationRepository.save(notification);
    }
}
