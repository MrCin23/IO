package pl.lodz.p.ias.io.poszkodowani.dto.financialneed;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinancialNeedCreateRequest {

    private Long userId;

    private Long mapPointId;

    @NotNull(message = "Description cannot be null")
    @Size(min = 5, max = 255, message = "Description must be between 5 and 255 characters")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;

    @Min(value = 1, message = "Collection goal must be at least 1")
    private double collectionGoal;

    //    @Min(value = 0, message = "Collection status must be non-negative")
//    private double collectionStatus;

    // Logic implemented in service
    //    @PastOrPresent(message = "Creation date cannot be in the future")
    //    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //    private Date creationDate;
    //

    //
    //    @Size(max = 50, message = "Status cannot exceed 50 characters")
    //    private String status;
    //
    //    @Min(value = 1, message = "Priority must be at least 1")
    //    private int priority;
}
