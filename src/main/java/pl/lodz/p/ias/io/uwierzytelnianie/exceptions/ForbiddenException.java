package pl.lodz.p.ias.io.uwierzytelnianie.exceptions;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}