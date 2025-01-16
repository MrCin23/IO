package pl.lodz.p.ias.io.darczyncy.exceptions;

import pl.lodz.p.ias.io.darczyncy.utils.I18n;


/**
 * Wyjątek zgłaszany, gdy nie udało się przeprowadzić płatności za darowiznę.
 * Rozszerza klasę DonationBaseException i używa komunikatów
 * zdefiniowanych w klasie I18n.
 */
public class PaymentFailedException extends DonationBaseException {

    /**
     * Konstruktor inicjujący wyjątek z komunikatem o nieudanej płatności za darowiznę.
     */
    public PaymentFailedException() {
        super(I18n.FINANCIAL_DONATION_PAYMENT_FAILED_EXCEPTION);
    }
}
