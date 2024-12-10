package pl.lodz.p.ias.io.darczyncy.resolvers;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.lodz.p.ias.io.darczyncy.dto.exception.ExceptionOutputDTO;
import pl.lodz.p.ias.io.darczyncy.exceptions.DonationBaseException;
import pl.lodz.p.ias.io.darczyncy.exceptions.FinancialDonationNotFoundException;
import pl.lodz.p.ias.io.darczyncy.exceptions.ItemDonationNotFoundException;
import pl.lodz.p.ias.io.darczyncy.exceptions.PaymentFailedException;

@Order(2)
@ControllerAdvice
public class DonationExceptionResolver {

    @ExceptionHandler(value = {FinancialDonationNotFoundException.class,
            ItemDonationNotFoundException.class})
    public ResponseEntity<?> handleDonationGeneralException(DonationBaseException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionOutputDTO(e.getMessage()));
    }

    @ExceptionHandler(value = {DonationBaseException.class})
    public ResponseEntity<?> handleDataIntegrityExceptions(DonationBaseException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionOutputDTO(e.getMessage()));
    }

    @ExceptionHandler(value = {PaymentFailedException.class})
    public ResponseEntity<?> handleDataIntegrityExceptions(PaymentFailedException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ExceptionOutputDTO(e.getMessage()));
    }
}
