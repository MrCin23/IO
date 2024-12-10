package pl.lodz.p.ias.io.darczyncy.exceptions;
import pl.lodz.p.ias.io.darczyncy.utils.I18n;

public class FinancialDonationNotFoundException extends RuntimeException {
    public FinancialDonationNotFoundException() {
        super(I18n.FINANCIAL_DONATION_NOT_FOUND_EXCEPTION);
    }
}
