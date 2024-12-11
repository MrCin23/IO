package pl.lodz.p.ias.io.poszkodowani.mapper;

import org.springframework.stereotype.Component;
import pl.lodz.p.ias.io.poszkodowani.dto.manualneed.ManualNeedCreateRequestDTO;
import pl.lodz.p.ias.io.poszkodowani.dto.manualneed.ManualNeedResponseDTO;
import pl.lodz.p.ias.io.poszkodowani.dto.materialneed.MaterialNeedResponseDTO;
import pl.lodz.p.ias.io.poszkodowani.model.ManualNeed;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ManualNeedMapper {
    public ManualNeed toManualNeed(ManualNeedCreateRequestDTO dto) {
        return ManualNeed.builder()
                .mapPointId(dto.getMapPointId())
                .description(dto.getDescription())
                .expirationDate(dto.getExpirationDate())
                .maxVolunteers(dto.getMaxVolunteers())
                .build();
    }

    public ManualNeedResponseDTO toManualNeedResponseDTO(ManualNeed manualNeed) {
        return ManualNeedResponseDTO.builder()
                .id(manualNeed.getId())
                .userId(manualNeed.getUser().getId())
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

    public List<ManualNeedResponseDTO> toManualNeedResponseDTOList(List<ManualNeed> manualNeeds) {
        return manualNeeds.stream()
                .map(this::toManualNeedResponseDTO)
                .collect(Collectors.toList());
    }
}
