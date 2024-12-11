package pl.lodz.p.ias.io.poszkodowani.dto.financialneed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinancialNeedResponseDTO {

    private Long id;
    private Long userId;
    private Long mapPointId;
    private String description;
    private Date creationDate;
    private Date expirationDate;
    private String status;
    private int priority;
    private double collectionStatus;
    private double collectionGoal;
}
