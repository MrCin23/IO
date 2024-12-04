package pl.lodz.p.ias.io.zasoby.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.zasoby.dto.ResourceDTO;
import pl.lodz.p.ias.io.zasoby.model.Resource;
import pl.lodz.p.ias.io.zasoby.repository.ResourceRepository;

@Service
public class ResourceService {
    private ResourceRepository resourceRepository;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }


    //todo: ogarnac pozniej tera mi sie nie chce 🤯
    public ResourceDTO addResource(ResourceDTO resourceDTO) {
        Resource resource = new Resource(resourceDTO.getResourceName(),
                resourceDTO.getResourceType(), resourceDTO.getResourceQuantity(),
                resourceDTO.getWarehouse());

        resourceRepository.create(resource);
        return new ResourceDTO(resource.getResourceName(), resource.getResourceType(),
                resource.getResourceQuantity(), resource.getResourceStatus(),
                resource.getWarehouse(), resource.getVolunteerName(), resource.getAssignedTask());
    }
}
