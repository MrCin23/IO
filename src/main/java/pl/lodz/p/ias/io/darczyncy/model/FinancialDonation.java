package pl.lodz.p.ias.io.darczyncy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;

import java.time.LocalDate;

/**
 * Reprezentuje darowiznę finansową w systemie.
 * Klasa ta dziedziczy po klasie {@link Donation} i zawiera szczegółowe informacje
 * o darowiźnie finansowej, takie jak potrzebę finansową, kwotę i walutę.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "financial_donations")
public class FinancialDonation extends Donation {

    /**
     * Dostępne waluty dla darowizn finansowych.
     */
    public enum Currency {
        PLN, EUR
    }

    /**
     * Potrzeba finansowa, na którą skierowana jest darowizna.
     * Powiązanie z encją {@link FinancialNeed} poprzez relację @ManyToOne.
     */
    @ManyToOne
    @JoinColumn(
            name = "financial_need_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "donation_need_id_fk")
    )
    private FinancialNeed need;

    /**
     * Kwota darowizny finansowej.
     */
    private double amount;

    /**
     * Waluta, w jakiej wyrażona jest kwota darowizny.
     */
    private Currency currency;

    /**
     * Tworzy nową instancję darowizny finansowej.
     *
     * @param donor      Konto darczyńcy powiązane z darowizną.
     * @param need       Potrzeba finansowa, na którą skierowana jest darowizna.
     * @param warehouseId Identyfikator magazynu powiązanego z darowizną.
     * @param amount     Kwota darowizny.
     * @param currency   Waluta darowizny.
     * @param localDate  Data utworzenia darowizny.
     */
    public FinancialDonation(Account donor, FinancialNeed need,
                             Long warehouseId,
                             double amount, Currency currency, LocalDate localDate) {
        super(donor, "money", "financial Donation", localDate, 1, warehouseId);
        this.need = need;
        this.amount = amount;
        this.currency = currency;
    }
}
