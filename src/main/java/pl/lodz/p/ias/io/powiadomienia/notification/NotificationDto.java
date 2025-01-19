package pl.lodz.p.ias.io.powiadomienia.notification;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * Klasa DTO służąca do tworzenia powiadomień.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto{
    @Length(min=1, max=1024)
    private String message;
    private NotificationType type;
    private Long userId;
}
