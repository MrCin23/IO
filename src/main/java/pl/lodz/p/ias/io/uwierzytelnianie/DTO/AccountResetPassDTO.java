package pl.lodz.p.ias.io.uwierzytelnianie.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResetPassDTO {
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    @NotBlank(message = "Password is required")
    private String password;
}
