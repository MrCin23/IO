package pl.lodz.p.ias.io.darczyncy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "financial_donations")
public class FinancialDonation extends Donation {

    public enum Currency {
        PLN, EUR
    }

    @ManyToOne
    @JoinColumn(
            name = "financial_need_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "donation_need_id_fk")
    )
    private FinancialNeed need;

    private double amount;

    private Currency currency;

    public FinancialDonation(Account donor, FinancialNeed need,
                             Long warehouseId,
                             double amount, Currency currency, LocalDate localDate) {
        super(donor, "money", "financial Donation", localDate, 1, warehouseId);
        this.need = need;
        this.amount = amount;
        this.currency = currency;
    }
}
