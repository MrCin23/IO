package pl.lodz.p.ias.io.poszkodowani.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
@SuperBuilder
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "need")
public abstract class Need {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "need_seq")
    @SequenceGenerator(name = "need_seq", sequenceName = "need_sequence", allocationSize = 1)
    @EqualsAndHashCode.Include
    @Column(name = "id") // Explicit column name
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "map_point_id", nullable = false)
    private Long mapPointId;

    @Column(name = "description", nullable = false)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiration_date", nullable = true)
    private Date expirationDate;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "priority", nullable = false)
    private int priority;
}