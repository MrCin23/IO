package pl.lodz.p.ias.io.powiadomienia.notification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.ias.io.powiadomienia.mock.MockUser;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto{
    private String message;
    private NotificationType type;
    private Long userId;
}
