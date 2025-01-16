package pl.lodz.p.ias.io.darczyncy.exceptions;

/**
 * Klasa reprezentująca bazowy wyjątek aplikacji.
 * Rozszerza klasę RuntimeException, umożliwiając zgłaszanie wyjątków
 * związanych z ogólnymi problemami aplikacyjnymi.
 */
public class ApplicationBaseException extends RuntimeException {

    /**
     * Konstruktor inicjujący wyjątek z określoną wiadomością.
     *
     * @param message Wiadomość opisująca przyczynę wyjątku.
     */
    public ApplicationBaseException(String message) {
        super(message);
    }
}
