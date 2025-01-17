package pl.lodz.p.ias.io.darczyncy.dto.exception;

/**
 * DTO reprezentujące komunikat o błędzie w odpowiedzi serwera.
 * Zawiera wiadomość, która opisuje przyczynę wyjątku.
 */
public record ExceptionOutputDTO (
        String message
) {}
