package pl.lodz.p.ias.io.darczyncy.resolvers;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.lodz.p.ias.io.darczyncy.exceptions.ApplicationBaseException;

import java.util.Arrays;

@Order(20)
@ControllerAdvice
public class GeneralResolver {

    @ExceptionHandler(value = {ApplicationBaseException.class})
    public ResponseEntity<?> handleDatabaseException(ApplicationBaseException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleAllExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
    }

}