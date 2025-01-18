package pl.lodz.p.ias.io.darczyncy.exceptions;
import pl.lodz.p.ias.io.darczyncy.utils.I18n;


/**
 * Wyjątek zgłaszany, gdy nie znaleziono darowizny rzeczowej.
 * Rozszerza klasę DonationBaseException i używa komunikatów
 * zdefiniowanych w klasie I18n.
 */
public class ItemDonationNotFoundException extends DonationBaseException {

    /**
     * Konstruktor inicjujący wyjątek z komunikatem o braku darowizny rzeczowej.
     */
    public ItemDonationNotFoundException() {
        super(I18n.ITEM_DONATION_NOT_FOUND_EXCEPTION);
    }
}
