package pl.lodz.p.ias.io.poszkodowani.mapper;

import org.springframework.stereotype.Component;
import pl.lodz.p.ias.io.poszkodowani.dto.materialneed.MaterialNeedCreateRequestDTO;
import pl.lodz.p.ias.io.poszkodowani.dto.materialneed.MaterialNeedResponseDTO;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MaterialNeedMapper {
    public MaterialNeed toMaterialNeed(MaterialNeedCreateRequestDTO dto) {
        return MaterialNeed.builder()
                .userId(dto.getUserId())
                .mapPointId(dto.getMapPointId())
                .description(dto.getDescription())
                .expirationDate(dto.getExpirationDate())
                .product(dto.getProduct())
                .amount(dto.getAmount())
                .build();
    }

    public MaterialNeedResponseDTO toMaterialNeedResponseDTO(MaterialNeed materialNeed) {
        return MaterialNeedResponseDTO.builder()
                .id(materialNeed.getId())
                .userId(materialNeed.getUserId())
                .mapPointId(materialNeed.getMapPointId())
                .description(materialNeed.getDescription())
                .creationDate(materialNeed.getCreationDate())
                .expirationDate(materialNeed.getExpirationDate())
                .status(materialNeed.getStatus())
                .priority(materialNeed.getPriority())
                .product(materialNeed.getProduct())
                .amount(materialNeed.getAmount())
                .build();
    }

    public List<MaterialNeedResponseDTO> toMaterialNeedResponseDTOList(List<MaterialNeed> materialNeeds) {
        return materialNeeds.stream()
                .map(this::toMaterialNeedResponseDTO)
                .collect(Collectors.toList());
    }
}
