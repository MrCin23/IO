package pl.lodz.p.ias.io.poszkodowani.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@SuperBuilder // Lombok annotation for hierarchical builders
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "financial_need")
public class FinancialNeed extends Need {

    @Column(name = "collection_status", nullable = false)
    private double collectionStatus;

    @Column(name = "collection_goal", nullable = false)
    private double collectionGoal;
}