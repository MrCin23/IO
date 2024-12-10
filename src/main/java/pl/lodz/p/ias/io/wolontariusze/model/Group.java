package pl.lodz.p.ias.io.wolontariusze.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @OneToMany
    private List<WUser> members;

    public void add(WUser user) {
        members.add(user);
    }

    public void remove(WUser user) {
        members.remove(user);
    }
}
