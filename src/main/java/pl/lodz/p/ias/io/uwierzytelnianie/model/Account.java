package pl.lodz.p.ias.io.uwierzytelnianie.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Account extends AbstractEntity {
    @Getter
    @Column(unique = true)
    private String username;

    @Getter
    @Setter
    private String passwordHash;

    @Getter
    private String firstName;

    @Getter
    private String lastName;

    @Column(nullable = true)
    @Setter
    private LocalDateTime lastLogin;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @Getter
    private Role role;

    public Account(String username, String passwordHash, Role role, String firstName, String lastName) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Account() {}

    public String getUserInfo() {
        return firstName + " " + lastName;
    }
}