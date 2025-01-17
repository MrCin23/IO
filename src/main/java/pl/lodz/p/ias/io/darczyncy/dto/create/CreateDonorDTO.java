package pl.lodz.p.ias.io.darczyncy.dto.create;

/**
 * DTO (Data Transfer Object) do tworzenia nowego darczyńcy.
 * Zawiera dane użytkownika, które będą wymagane przy rejestracji darczyńcy.
 */
public record CreateDonorDTO (
        String userName,
        String firstName,
        String lastName,
        String password
) {}
