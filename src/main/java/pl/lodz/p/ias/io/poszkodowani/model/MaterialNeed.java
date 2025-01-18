package pl.lodz.p.ias.io.poszkodowani.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "material_need")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialNeed extends Need {
    public enum ItemCategory {
        CLOTHING, HOUSEHOLD, FOOD, TOYS, BOOKS
    }

    @Column(name = "item_category", nullable = false)
    private ItemCategory itemCategory;
}