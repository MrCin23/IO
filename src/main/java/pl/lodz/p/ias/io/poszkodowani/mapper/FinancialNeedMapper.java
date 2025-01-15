package pl.lodz.p.ias.io.poszkodowani.mapper;

import org.springframework.stereotype.Component;
import pl.lodz.p.ias.io.poszkodowani.dto.financialneed.FinancialNeedCreateRequest;
import pl.lodz.p.ias.io.poszkodowani.dto.financialneed.FinancialNeedResponse;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FinancialNeedMapper {

    public FinancialNeed toFinancialNeed(FinancialNeedCreateRequest dto) {
        return FinancialNeed.builder()
                .description(dto.getDescription())
                .collectionGoal(dto.getCollectionGoal())
                .build();
    }

    public FinancialNeedResponse toFinancialNeedResponse(FinancialNeed financialNeed) {
        return FinancialNeedResponse.builder()
                .id(financialNeed.getId())
                .userId(financialNeed.getUser().getId())
                .mapPointId(financialNeed.getMapPoint().getPointID())
                .description(financialNeed.getDescription())
                .creationDate(financialNeed.getCreationDate())
                .expirationDate(financialNeed.getExpirationDate())
                .status(financialNeed.getStatus())
                .priority(financialNeed.getPriority())
                .collectionStatus(financialNeed.getCollectionStatus())
                .collectionGoal(financialNeed.getCollectionGoal())
                .build();
    }

    public List<FinancialNeedResponse> toFinancialNeedResponseList(List<FinancialNeed> financialNeeds) {
        return financialNeeds.stream()
                .map(this::toFinancialNeedResponse)
                .collect(Collectors.toList());
    }
}
