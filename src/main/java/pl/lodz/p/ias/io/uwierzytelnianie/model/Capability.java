package pl.lodz.p.ias.io.uwierzytelnianie.model;

import jakarta.persistence.*;

@Entity
public class Capability extends AbstractEntity {
    private String capabilityName;

    public Capability() {}

    public Capability(String capabilityName) {
        this.capabilityName = capabilityName;
    }
}

