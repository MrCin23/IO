package pl.lodz.p.ias.io.darczyncy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.zasoby.utils.ResourceStatus;

import java.time.LocalDate;

/**
 * Reprezentuje darowiznę rzeczową w systemie.
 * Klasa rozszerza klasę bazową {@link Donation} i dodaje szczegóły dotyczące darowizn przedmiotów, takich jak opis, kategoria oraz powiązanie z potrzebą materialną.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "item_donations")
public class ItemDonation extends Donation {

    /**
     * Kategorie przedmiotów, które mogą być przekazywane w darowiźnie.
     */
    public enum ItemCategory {
        CLOTHING,    // Odzież
        HOUSEHOLD,   // Wyposażenie domowe
        FOOD,        // Żywność
        TOYS,        // Zabawki
        BOOKS        // Książki
    }

    /**
     * Opis darowizny.
     */
    private String description;

    /**
     * Kategoria przedmiotu w darowiźnie.
     */
    private ItemCategory category;

    /**
     * Powiązana potrzeba materialna, którą darowizna zaspokaja.
     */
    @ManyToOne
    @JoinColumn(
            name = "material_need_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "donation_need_id_fk")
    )
    private MaterialNeed need;

    /**
     * Konstruktor tworzący darowiznę rzeczową z podanymi szczegółami.
     *
     * @param donor           Konto darczyńcy realizującego darowiznę.
     * @param need            Potrzeba materialna, którą darowizna zaspokaja.
     * @param itemName        Nazwa przedmiotu w darowiźnie.
     * @param resourceQuantity Ilość przedmiotów przekazywanych w darowiźnie.
     * @param warehouseId     Identyfikator magazynu, w którym przechowywana jest darowizna.
     * @param category        Kategoria przedmiotu.
     * @param description     Opis darowizny.
     * @param localDate       Data przekazania darowizny.
     */
    public ItemDonation(Account donor, MaterialNeed need, String itemName,
                        int resourceQuantity, long warehouseId,
                        ItemCategory category, String description, LocalDate localDate) {
        super(donor, itemName, "Item donation", localDate, resourceQuantity, warehouseId, ResourceStatus.PENDING);
        this.need = need;
        this.description = description;
        this.category = category;
    }
}
