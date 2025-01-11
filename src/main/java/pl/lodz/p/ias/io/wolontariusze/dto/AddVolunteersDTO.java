package pl.lodz.p.ias.io.wolontariusze.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class AddVolunteersDTO {
    @NotNull
    @NotEmpty
    Set<Long> members;
}
