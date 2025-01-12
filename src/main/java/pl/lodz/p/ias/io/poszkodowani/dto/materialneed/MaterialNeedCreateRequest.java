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

    private Long userId;

    private Long mapPointId;

    // Fields from Need class
    @NotNull(message = "Description cannot be null")
    @Size(min = 5, max = 255, message = "Description must be between 5 and 255 characters")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;

    @NotNull(message = "Product name cannot be null")
    private MaterialNeed.ItemCategory itemCategory;


    //    @Size(max = 50, message = "Status cannot exceed 50 characters")
    //    private String status;
    //
    //    @Min(value = 1, message = "Priority must be at least 1")
    //    private int priority;

    // Logic implemented in service
    //    @PastOrPresent(message = "Creation date cannot be in the future")
    //    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //    private Date creationDate;

}
