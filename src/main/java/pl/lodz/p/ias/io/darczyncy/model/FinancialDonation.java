package pl.lodz.p.ias.io.darczyncy.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.ias.io.poszkodowani.model.Need;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;

@Getter @Setter
@NoArgsConstructor
@Entity
public class FinancialDonation extends Donation {

    public enum Currency {
        PLN, EUR
    }

    private double amount;
    private double calculatedVAT;
    private Currency currency;

    public FinancialDonation(Account donor, Need need,
                             long warehouseId,
                             double amount, double calculatedVAT, Currency currency) {
        super(donor, need, "financial Donation", "money", 1, warehouseId);
        this.amount = amount;
        this.calculatedVAT = calculatedVAT;
        this.currency = currency;
    }
}
