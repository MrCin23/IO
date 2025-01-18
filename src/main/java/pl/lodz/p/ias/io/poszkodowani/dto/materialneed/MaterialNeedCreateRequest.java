package pl.lodz.p.ias.io.poszkodowani.dto.materialneed;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaterialNeedCreateRequest {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Map point ID cannot be null")
    private Long mapPointId;

    @NotNull(message = "Description cannot be null")
    @Size(min = 5, max = 255, message = "Description must be between 5 and 255 characters")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;

    @NotNull(message = "Product name cannot be null")
    private MaterialNeed.ItemCategory itemCategory;
}
