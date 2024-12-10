package pl.lodz.p.ias.io.wolontariusze.model;

import jakarta.persistence.*;
import lombok.Data;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Users;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @OneToMany
    private ArrayList<Users> members;

    public Group() {

    }

    public void add(Users user) {
        members.add(user);
    }

    public Group(String name) {
        this.name = name;
        this.members = new ArrayList<>();
    }

    public void remove(Users user) {
        members.remove(user);
    }
}
