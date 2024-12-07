package pl.lodz.p.ias.io.powiadomienia.announcement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.ias.io.powiadomienia.mock.MockUser;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserAnnouncement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private MockUser user;

    @ManyToOne
    private Announcement announcement;
    private boolean read;
}
