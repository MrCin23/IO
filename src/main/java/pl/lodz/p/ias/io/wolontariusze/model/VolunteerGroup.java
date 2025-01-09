package pl.lodz.p.ias.io.wolontariusze.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Users;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class VolunteerGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany
    @JoinTable(
            name = "group_users", // Join table
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Users> members;

    public VolunteerGroup(String name, Set<Users> members) {
        this.name = name;
        this.members = members;
    }

    public void add(Users user) {
        members.add(user);
    }

    public void addMembers(Set<Users> members) {
        this.members.addAll(members);
    }

    public void remove(Users user) {
        members.remove(user);
    }
}
