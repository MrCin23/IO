package pl.lodz.p.ias.io.darczyncy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ItemDonation extends Donation{
    public enum ItemCategory {
        CLOTHING, HOUSEHOLD, FOOD, TOYS, BOOKS
    }

    private String description;

    public ItemDonation(User donor, Need need, String itemName,
                        int resourceQuantity, long warehouseId,
                        ItemCategory category, String description) {
        super(donor, need, itemName, category.toString(), resourceQuantity, warehouseId);
        this.description = description;
    }
}
