package pl.lodz.p.ias.io.uwierzytelnianie;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Set;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    private String roleName;

    @Getter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_capability",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "capability_id"))
    private Set<Capability> capabilities;

    public void assignCapabilities(Set<Capability> newCapabilities) {
        this.capabilities.addAll(newCapabilities);
    }

    public void removeCapability(Capability capability) {
        this.capabilities.remove(capability);
    }
}

