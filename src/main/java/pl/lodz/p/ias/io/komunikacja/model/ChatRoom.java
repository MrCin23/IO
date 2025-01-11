package pl.lodz.p.ias.io.komunikacja.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;

import java.util.List;

@Entity
@Getter
@Setter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "chat_room_users",
            joinColumns = @JoinColumn(name = "chat_room_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id")
    )
    private List<Account> users;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Message> messages;
}
