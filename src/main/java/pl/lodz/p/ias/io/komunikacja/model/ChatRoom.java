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

    @OneToMany
    private List<Account> users;

    @OneToMany
    private List<Message> messages;
}
