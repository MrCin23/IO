package pl.lodz.p.ias.io.poszkodowani.mapper;

import org.springframework.stereotype.Component;
import pl.lodz.p.ias.io.poszkodowani.dto.manualneed.ManualNeedCreateRequest;
import pl.lodz.p.ias.io.poszkodowani.dto.manualneed.ManualNeedResponse;
import pl.lodz.p.ias.io.poszkodowani.model.ManualNeed;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ManualNeedMapper {
    public ManualNeed toManualNeed(ManualNeedCreateRequest dto) {
        return ManualNeed.builder()
                .description(dto.getDescription())
                .expirationDate(dto.getExpirationDate())
                .maxVolunteers(dto.getMaxVolunteers())
                .build();
    }

    public ManualNeedResponse toManualNeedResponse(ManualNeed manualNeed) {
        return ManualNeedResponse.builder()
                .id(manualNeed.getId())
                .userId(manualNeed.getUser().getId())
                .mapPointId(manualNeed.getMapPoint().getPointID())
                .description(manualNeed.getDescription())
                .creationDate(manualNeed.getCreationDate())
                .expirationDate(manualNeed.getExpirationDate())
                .status(manualNeed.getStatus())
                .priority(manualNeed.getPriority())
                .volunteers(manualNeed.getVolunteers())
                .maxVolunteers(manualNeed.getMaxVolunteers())
                .build();
    }

    public List<ManualNeedResponse> toManualNeedResponseList(List<ManualNeed> manualNeeds) {
        return manualNeeds.stream()
                .map(this::toManualNeedResponse)
                .collect(Collectors.toList());
    }
}
