package pl.lodz.p.ias.io.darczyncy.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.ias.io.poszkodowani.model.Need;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Users;

@Getter @Setter
@NoArgsConstructor
@Entity
public class ItemDonation extends Donation{
    public enum ItemCategory {
        CLOTHING, HOUSEHOLD, FOOD, TOYS, BOOKS
    }

    private String description;

    public ItemDonation(Users donor, Need need, String itemName,
                        int resourceQuantity, long warehouseId,
                        ItemCategory category, String description) {
        super(donor, need, itemName, category.toString(), resourceQuantity, warehouseId);
        this.description = description;
    }
}
