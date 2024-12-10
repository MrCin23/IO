package pl.lodz.p.ias.io.poszkodowani.mapper;

import org.springframework.stereotype.Component;
import pl.lodz.p.ias.io.poszkodowani.dto.manualneed.ManualNeedCreateRequestDTO;
import pl.lodz.p.ias.io.poszkodowani.dto.manualneed.ManualNeedResponseDTO;
import pl.lodz.p.ias.io.poszkodowani.dto.materialneed.MaterialNeedResponseDTO;
import pl.lodz.p.ias.io.poszkodowani.model.ManualNeed;

@Component
public class ManualNeedMapper {
    public ManualNeed toManualNeed(ManualNeedCreateRequestDTO dto) {
        return ManualNeed.builder()
                .userId(dto.getUserId())
                .mapPointId(dto.getMapPointId())
                .description(dto.getDescription())
                .expirationDate(dto.getExpirationDate())
                .maxVolunteers(dto.getMaxVolunteers())
                .build();
    }

    public ManualNeedResponseDTO toManualNeedResponseDTO(ManualNeed manualNeed) {
        return ManualNeedResponseDTO.builder()
                .id(manualNeed.getId())
                .userId(manualNeed.getUserId())
                .mapPointId(manualNeed.getMapPointId())
                .description(manualNeed.getDescription())
                .creationDate(manualNeed.getCreationDate())
                .expirationDate(manualNeed.getExpirationDate())
                .status(manualNeed.getStatus())
                .priority(manualNeed.getPriority())
                .volunteers(manualNeed.getVolunteers())
                .maxVolunteers(manualNeed.getMaxVolunteers())
                .build();
    }
}
