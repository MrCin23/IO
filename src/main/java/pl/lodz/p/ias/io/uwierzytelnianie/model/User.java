package pl.lodz.p.ias.io.uwierzytelnianie.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    private String passwordHash;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @Getter
    private Role role;

    private String firstName;
    private String lastName;

    @Column(nullable = true)
    @Setter
    private LocalDateTime lastLogin;

    public User(String username, String passwordHash, Role role, String firstName, String lastName) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {}

    public String getUserInfo() {
        return firstName + " " + lastName;
    }

    public Role getUserRole() {
        return this.role;
    }
}