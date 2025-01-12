package pl.lodz.p.ias.io.darczyncy.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.ias.io.poszkodowani.model.Need;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;

@Getter @Setter
@NoArgsConstructor
@Entity
@SuperBuilder(toBuilder = true)
public class ItemDonation extends Donation{
    public enum ItemCategory {
        CLOTHING, HOUSEHOLD, FOOD, TOYS, BOOKS
    }

    private String description;

    private ItemCategory category;

    public ItemDonation(Account donor, Need need, String itemName,
                        int resourceQuantity, long warehouseId,
                        ItemCategory category, String description) {
        super(donor, need, itemName, "Item donation", resourceQuantity, warehouseId);
        this.description = description;
        this.category = category;
    }
}
