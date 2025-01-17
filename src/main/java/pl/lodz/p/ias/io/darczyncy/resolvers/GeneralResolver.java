package pl.lodz.p.ias.io.darczyncy.resolvers;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.lodz.p.ias.io.darczyncy.dto.exception.ExceptionOutputDTO;
import pl.lodz.p.ias.io.darczyncy.exceptions.ApplicationBaseException;
import pl.lodz.p.ias.io.darczyncy.exceptions.DonationBaseException;

import java.util.Arrays;

/**
 * Klasa ta służy do obsługi wyjątków aplikacji. Jest odpowiedzialna za zwracanie odpowiednich odpowiedzi HTTP
 * w przypadku wystąpienia błędów związanych z bazą danych i innymi ogólnymi wyjątkami.
 */
@Order(20)
@ControllerAdvice
public class GeneralResolver {

    /**
     * Obsługuje wyjątek związany z błędem aplikacji (np. błąd bazy danych).
     * Zwraca odpowiedź HTTP z kodem 500 (INTERNAL SERVER ERROR) oraz ogólnym komunikatem błędu.
     *
     * @param e wyjątek rzucony w przypadku błędu aplikacji (np. błąd w bazie danych)
     * @return odpowiedź HTTP z kodem 500 i ogólnym komunikatem o błędzie
     */
    @ExceptionHandler(value = {ApplicationBaseException.class})
    public ResponseEntity<?> handleDatabaseException(ApplicationBaseException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionOutputDTO("database exception occurred"));
    }

}