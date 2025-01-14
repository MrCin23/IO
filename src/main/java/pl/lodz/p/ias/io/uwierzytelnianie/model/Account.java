package pl.lodz.p.ias.io.uwierzytelnianie.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Account extends AbstractEntity {
    @Getter
    @Column(unique = true)
    private String username;

    /*@Getter
    @Column(unique = true)
    private String email;*/

    @Getter
    @Setter
    @JsonIgnore
    private String passwordHash;

    @Getter
    private String firstName;

    @Getter
    private String lastName;

    @Getter
    @Setter
    private boolean active;

    @Column(nullable = true)
    @Setter
    @Getter
    private LocalDateTime lastLogin;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @Getter
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