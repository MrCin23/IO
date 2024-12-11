package pl.lodz.p.ias.io.poszkodowani.mapper;

import org.springframework.stereotype.Component;
import pl.lodz.p.ias.io.poszkodowani.dto.financialneed.FinancialNeedCreateRequestDTO;
import pl.lodz.p.ias.io.poszkodowani.dto.financialneed.FinancialNeedResponseDTO;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FinancialNeedMapper {

    public FinancialNeed toFinancialNeed(FinancialNeedCreateRequestDTO dto) {
        return FinancialNeed.builder()
                .userId(dto.getUserId())
                .mapPointId(dto.getMapPointId())
                .description(dto.getDescription())
                .collectionGoal(dto.getCollectionGoal())
                .build();
    }

    public FinancialNeedResponseDTO toFinancialNeedResponseDTO(FinancialNeed financialNeed) {
        return FinancialNeedResponseDTO.builder()
                .id(financialNeed.getId())
                .description(financialNeed.getDescription())
                .creationDate(financialNeed.getCreationDate())
                .expirationDate(financialNeed.getExpirationDate())
                .status(financialNeed.getStatus())
                .priority(financialNeed.getPriority())
                .collectionStatus(financialNeed.getCollectionStatus())
                .collectionGoal(financialNeed.getCollectionGoal())
                .build();
    }

    public List<FinancialNeedResponseDTO> toFinancialNeedResponseDTOList(List<FinancialNeed> financialNeeds) {
        return financialNeeds.stream()
                .map(this::toFinancialNeedResponseDTO)
                .collect(Collectors.toList());
    }
}
