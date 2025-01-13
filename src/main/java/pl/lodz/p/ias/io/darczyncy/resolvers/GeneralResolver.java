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

@Order(20)
@ControllerAdvice
public class GeneralResolver {

    @ExceptionHandler(value = {ApplicationBaseException.class})
    public ResponseEntity<?> handleDatabaseException(ApplicationBaseException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionOutputDTO("database exception occurred"));
    }

}