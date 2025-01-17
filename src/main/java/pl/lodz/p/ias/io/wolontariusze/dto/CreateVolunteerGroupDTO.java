package pl.lodz.p.ias.io.wolontariusze.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateVolunteerGroupDTO {
    @NotEmpty
    private String groupName;
}
