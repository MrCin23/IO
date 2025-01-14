package pl.lodz.p.ias.io.uwierzytelnianie.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Role extends AbstractEntity {
    @Getter
    private String roleName;

    public Role() {}

    public Role(String roleName) {
        this.roleName = roleName;
    }
}

