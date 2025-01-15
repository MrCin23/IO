package pl.lodz.p.ias.io.uwierzytelnianie.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountChangeRoleDTO {
    @NotBlank(message = "Role name is required")
    private String roleName;
}
