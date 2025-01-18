package pl.lodz.p.ias.io.poszkodowani.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "manual_need")
public class ManualNeed extends Need {

    @Column(name = "volunteers", nullable = false)
    private int volunteers;

    @Column(name = "max_volunteers", nullable = false)
    private int maxVolunteers;
}