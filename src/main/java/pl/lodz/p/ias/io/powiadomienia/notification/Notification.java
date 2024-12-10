package pl.lodz.p.ias.io.powiadomienia.notification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Users;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String message;
    private NotificationType type;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    private boolean read;

}
