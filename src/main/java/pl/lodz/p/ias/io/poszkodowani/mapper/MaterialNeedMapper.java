package pl.lodz.p.ias.io.poszkodowani.mapper;

import org.springframework.stereotype.Component;
import pl.lodz.p.ias.io.poszkodowani.dto.materialneed.MaterialNeedCreateRequest;
import pl.lodz.p.ias.io.poszkodowani.dto.materialneed.MaterialNeedResponse;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MaterialNeedMapper {
    public MaterialNeed toMaterialNeed(MaterialNeedCreateRequest dto) {
        return MaterialNeed.builder()
                .description(dto.getDescription())
                .expirationDate(dto.getExpirationDate())
                .itemCategory(dto.getItemCategory())
                .build();
    }

    public MaterialNeedResponse toMaterialNeedResponse(MaterialNeed materialNeed) {
        return MaterialNeedResponse.builder()
                .id(materialNeed.getId())
                .userId(materialNeed.getUser().getId())
                .mapPointId(materialNeed.getMapPoint().getPointID())
                .description(materialNeed.getDescription())
                .creationDate(materialNeed.getCreationDate())
                .expirationDate(materialNeed.getExpirationDate())
                .status(materialNeed.getStatus())
                .priority(materialNeed.getPriority())
                .itemCategory(materialNeed.getItemCategory())
                .build();
    }

    public List<MaterialNeedResponse> toMaterialNeedResponseList(List<MaterialNeed> materialNeeds) {
        return materialNeeds.stream()
                .map(this::toMaterialNeedResponse)
                .collect(Collectors.toList());
    }
}
