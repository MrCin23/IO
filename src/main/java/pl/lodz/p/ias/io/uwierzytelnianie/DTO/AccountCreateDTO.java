package pl.lodz.p.ias.io.uwierzytelnianie.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreateDTO {
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @NotBlank(message = "Username is required")
    private String username;

    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    @NotBlank(message = "Password is required")
    private String password;

    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Role name is required")
    private String roleName;

}
