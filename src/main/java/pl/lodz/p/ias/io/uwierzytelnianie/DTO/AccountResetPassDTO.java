package pl.lodz.p.ias.io.uwierzytelnianie.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResetPassDTO {
    @NotBlank(message = "Password is required")
    private String password;
}
