package pl.lodz.p.ias.io.zasoby.service;

import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.zasoby.model.Resource;
import pl.lodz.p.ias.io.zasoby.repository.ResourceRepository;

import java.util.List;

@Service
public class ResourceReportService {

    private final ResourceRepository resourceRepository;

    public ResourceReportService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public String generateResourceUsageReport() {
        List<Resource> resources = resourceRepository.findAll();

        StringBuilder report = new StringBuilder();
        report.append("Resource Usage Report\n");
        report.append("=================================\n");

        resources.forEach(resource -> report.append(String.format(
                "Resource Name: %s\nType: %s\nQuantity: %d\nWarehouse ID: %d\n\n",
                resource.getResourceName(),
                resource.getResourceType(),
                resource.getResourceQuantity(),
                resource.getWarehouseId()
        )));

        return report.toString();
    }
}

