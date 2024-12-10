package pl.lodz.p.ias.io.zasoby.utils;

import pl.lodz.p.ias.io.zasoby.dto.ResourceDTO;
import pl.lodz.p.ias.io.zasoby.model.Resource;

public class ResourceConverter {
    public ResourceDTO convertResourceToDTO(Resource resource) {
        return new ResourceDTO(
                resource.getResourceName(),
                resource.getResourceType(),
                resource.getResourceQuantity(),
                resource.getResourceStatus(),
                resource.getWarehouseId(),
                resource.getVolunteerName(),
                resource.getAssignedTask()
        );
    }

    public Resource convertDTOToResource(ResourceDTO resourceDTO) {
        return new Resource(
                resourceDTO.getResourceName(),
                resourceDTO.getResourceType(),
                resourceDTO.getResourceQuantity(),
                resourceDTO.getWarehouseId()
        );
    }
}