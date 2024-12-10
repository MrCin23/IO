package pl.lodz.p.ias.io.uwierzytelnianie;

import jakarta.persistence.*;

@Entity
public class Capability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long capabilityId;

    private String capabilityName;

    public Capability() {}

    public Capability(String capabilityName) {
        this.capabilityName = capabilityName;
    }
}

