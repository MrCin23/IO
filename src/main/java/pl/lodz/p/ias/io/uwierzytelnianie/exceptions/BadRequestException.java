package pl.lodz.p.ias.io.uwierzytelnianie.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
