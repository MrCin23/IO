package pl.lodz.p.ias.io.poszkodowani.dto.manualneed;

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
public class ManualNeedResponseDTO {

    private Long id;
    private Long userId;
    private Long mapPointId;
    private String description;
    private Date creationDate;
    private Date expirationDate;
    private String status;
    private int priority;
    private int volunteers;
    private int maxVolunteers;

}