package pl.lodz.p.ias.io.zasoby.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.zasoby.model.Resource;
import pl.lodz.p.ias.io.zasoby.repository.ResourceRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ResourceAlertService {

    private final ResourceRepository resourceRepository;

    public void checkLowResourceLevels() {
        List<Resource> lowResources = resourceRepository.findAll().stream()
                .filter(resource -> resource.getResourceQuantity() < resource.getAlertThreshold())
                .toList();

        lowResources.forEach(this::sendAlert);
    }

    private void sendAlert(Resource resource) {
        String alertMessage = String.format(
                "ALERT: Resource '%s' in warehouse %d has low quantity (%d left, below threshold %d).",
                resource.getResourceName(),
                resource.getWarehouseId(),
                resource.getResourceQuantity(),
                resource.getAlertThreshold()
        );
        System.out.println(alertMessage);
    }
}
