package pl.lodz.p.ias.io.darczyncy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "item_donations")
public class ItemDonation extends Donation{
    public enum ItemCategory {
        CLOTHING, HOUSEHOLD, FOOD, TOYS, BOOKS
    }

    private String description;

    private ItemCategory category;

    @ManyToOne
    @JoinColumn(
            name = "material_need_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "donation_need_id_fk")
    )
    private MaterialNeed need;

    public ItemDonation(Account donor, MaterialNeed need, String itemName,
                        int resourceQuantity, long warehouseId,
                        ItemCategory category, String description, LocalDate localDate) {
        super(donor, itemName, "Item donation", localDate, resourceQuantity, warehouseId);
        this.need = need;
        this.description = description;
        this.category = category;
    }
}
