package pl.lodz.p.ias.io.wolontariusze.model;

import jakarta.persistence.*;
import lombok.Data;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Users;

import java.util.List;

@Entity
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @OneToMany
    private List<Users> members;

    public void add(Users user) {
        members.add(user);
    }

    public void remove(Users user) {
        members.remove(user);
    }
}
