package pl.lodz.p.ias.io.uwierzytelnianie.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Role;

@Getter
@Setter
public class UserCreateDTO {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Role name is required")
    private String roleName;
}
