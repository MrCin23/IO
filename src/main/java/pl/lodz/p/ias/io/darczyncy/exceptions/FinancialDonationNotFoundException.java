package pl.lodz.p.ias.io.darczyncy.exceptions;
import pl.lodz.p.ias.io.darczyncy.utils.I18n;


/**
 * Wyjątek zgłaszany, gdy nie znaleziono darowizny finansowej.
 * Rozszerza klasę DonationBaseException i używa komunikatów
 * zdefiniowanych w klasie I18n.
 */
public class FinancialDonationNotFoundException extends DonationBaseException {

    /**
     * Konstruktor inicjujący wyjątek z komunikatem o braku darowizny finansowej.
     */
    public FinancialDonationNotFoundException() {
        super(I18n.FINANCIAL_DONATION_NOT_FOUND_EXCEPTION);
    }
}
