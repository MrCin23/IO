package pl.lodz.p.ias.io.uwierzytelnianie.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Role extends AbstractEntity {
    @Getter
    private String roleName;

    @Getter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_capability",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "capability_id"))
    private Set<Capability> capabilities;

    public Role() {}

    public Role(String roleName) {
        this.roleName = roleName;
        this.capabilities = new HashSet<>();
    }

    public void assignCapabilities(Set<Capability> newCapabilities) {
        this.capabilities.addAll(newCapabilities);
    }

    public void removeCapability(Capability capability) {
        this.capabilities.remove(capability);
    }

    public void addCapability(Capability capability) {
        this.capabilities.add(capability);
    }

    public void removeCapabilities(Set<Capability> capabilitiesToRemove) {
        this.capabilities.removeAll(capabilitiesToRemove);
    }
}

