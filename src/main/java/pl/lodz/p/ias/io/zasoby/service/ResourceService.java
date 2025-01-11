package pl.lodz.p.ias.io.zasoby.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ias.io.zasoby.dto.ResourceDTO;
import pl.lodz.p.ias.io.zasoby.model.Resource;
import pl.lodz.p.ias.io.zasoby.repository.ResourceRepository;
import pl.lodz.p.ias.io.zasoby.utils.ResourceConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResourceService {
    private final ResourceRepository resourceRepository;
    private final ResourceConverter converter = new ResourceConverter();

//    @Autowired
//    public ResourceService(ResourceRepository resourceRepository) {
//        this.resourceRepository = resourceRepository;
//    }

    public ResourceDTO addResource(ResourceDTO resourceDTO) {
        Resource resource = converter.convertDTOToResource(resourceDTO);
        resourceRepository.save(resource);
        return converter.convertResourceToDTO(resource);
    }

    public ResourceDTO getResourceById(long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resource with id " + id + " not found"));
        return converter.convertResourceToDTO(resource);
    }

    public List<ResourceDTO> getAllResources() {
        return resourceRepository.findAll()
                .stream()
                .map(converter::convertResourceToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateResource(long id, @Valid ResourceDTO resourceDTO) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resource with id " + id + " not found"));

        resource.setResourceName(resourceDTO.getResourceName());
        resource.setResourceType(resourceDTO.getResourceType());
        resource.setResourceQuantity(resourceDTO.getResourceQuantity());
        resource.setResourceStatus(resourceDTO.getResourceStatus());
        resource.setWarehouseId(resourceDTO.getWarehouseId());
        resource.setVolunteerName(resourceDTO.getVolunteerName());
        resource.setAssignedTask(resourceDTO.getAssignedTask());
    }

    public void deleteResource(long id) {
        if (resourceRepository.existsById(id)) {
            resourceRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Resource with id " + id + " does not exist");
        }
    }
}