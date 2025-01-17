package pl.lodz.p.ias.io.wolontariusze.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.lang.Contract;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;

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

    @ManyToMany
    @JoinTable(
            name = "group_users", // Join table
            joinColumns = @JoinColumn(name = "group_id")
//            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Account> members;

    public VolunteerGroup(String name, Set<Account> members) {
        this.name = name;
        this.members = members;
    }

    public void add(Account user) {
        members.add(user);
    }

    public void addMembers(Set<Account> members) {
        this.members.addAll(members);
    }

    public void removeMembers(Set<Account> members) {
        this.members.removeAll(members);
    }
}
