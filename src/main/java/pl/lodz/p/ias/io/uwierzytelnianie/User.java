package pl.lodz.p.ias.io.uwierzytelnianie;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    private String passwordHash;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private String firstName;
    private String lastName;
    private String lastLogin;

    public String getUserInfo() {
        return firstName + " " + lastName;
    }

    public Role getUserRole() {
        return this.role;
    }
}