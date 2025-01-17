package pl.lodz.p.ias.io.uwierzytelnianie.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Account extends AbstractEntity {
    @Column(unique = true)
    private String username;

    /*@Getter
    @Column(unique = true)
    private String email;*/

    @Setter
    @JsonIgnore
    private String passwordHash;

    private String firstName;

    private String lastName;

    @Setter
    private boolean active;

    @Column(nullable = true)
    @Setter
    private LocalDateTime lastLogin;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @Setter
    private Role role;

    public Account(String username, String passwordHash, Role role, String firstName, String lastName) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = true;
    }

    public Account() {}

    public String getUserInfo() {
        return firstName + " " + lastName;
    }
}