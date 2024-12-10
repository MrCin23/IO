package pl.lodz.p.ias.io.powiadomienia.notification;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto{
    private String message;
    private NotificationType type;
    private Long userId;
}
