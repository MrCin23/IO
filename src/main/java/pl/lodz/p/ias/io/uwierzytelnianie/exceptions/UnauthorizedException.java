package pl.lodz.p.ias.io.uwierzytelnianie.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}