package pl.lodz.p.ias.io.darczyncy.resolvers;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.lodz.p.ias.io.darczyncy.dto.exception.ExceptionOutputDTO;
import pl.lodz.p.ias.io.darczyncy.exceptions.DonationBaseException;
import pl.lodz.p.ias.io.darczyncy.exceptions.ItemDonationNotFoundException;
import pl.lodz.p.ias.io.darczyncy.exceptions.PaymentFailedException;

/**
 * Klasa ta odpowiada za obsługę wyjątków związanych z procesem darowizn.
 * Zawiera metody, które przechwytują specyficzne wyjątki i zwracają odpowiednią odpowiedź HTTP.
 */
@Order(2)
@ControllerAdvice
public class DonationExceptionResolver {

    /**
     * Obsługuje wyjątek związany z nieudanym procesem płatności.
     * Zwraca odpowiedź HTTP z kodem 503 (SERVICE UNAVAILABLE) oraz komunikatem błędu.
     *
     * @param e wyjątek, który został rzucony podczas nieudanego procesu płatności
     * @return odpowiedź HTTP z kodem 503 i szczegółami wyjątku
     */
    @ExceptionHandler(value = {PaymentFailedException.class})
    public ResponseEntity<?> handleDonationExceptions(PaymentFailedException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ExceptionOutputDTO(e.getMessage()));
    }

    /**
     * Obsługuje wyjątek związany z błędem w bazie darowizn.
     * Zwraca odpowiedź HTTP z kodem 400 (BAD REQUEST) oraz komunikatem błędu.
     *
     * @param e wyjątek, który został rzucony w przypadku błędu bazowego darowizn
     * @return odpowiedź HTTP z kodem 400 i szczegółami wyjątku
     */
    @ExceptionHandler(value = {DonationBaseException.class})
    public ResponseEntity<?> handleDonationBaseExceptions(DonationBaseException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionOutputDTO(e.getMessage()));
    }
}
