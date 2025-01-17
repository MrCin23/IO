package pl.lodz.p.ias.io.darczyncy.exceptions;

/**
 * Klasa reprezentująca bazowy wyjątek dla darowizn.
 * Rozszerza klasę ApplicationBaseException i jest używana do zgłaszania wyjątków
 * związanych z błędami dotyczącymi darowizn.
 */
public class DonationBaseException extends ApplicationBaseException {

    /**
     * Konstruktor inicjujący wyjątek z określoną wiadomością.
     *
     * @param message Wiadomość opisująca przyczynę wyjątku.
     */
    public DonationBaseException(String message) {
        super(message);
    }


}
