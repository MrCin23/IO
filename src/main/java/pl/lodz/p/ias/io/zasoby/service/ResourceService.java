package pl.lodz.p.ias.io.zasoby.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.uwierzytelnianie.enums.UserRole;
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

    public ResourceDTO addResource(ResourceDTO resourceDTO) {
        Resource resource = converter.convertDTOToResource(resourceDTO);
        resourceRepository.save(resource);
        return converter.convertResourceToDTO(resource);
    }

    public ResourceDTO getResourceById(Long id) {
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

//    @Transactional
    public void updateResource(Long id, @Valid ResourceDTO resourceDTO) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resource with id " + id + " not found"));

        resource.setResourceName(resourceDTO.getResourceName());
        resource.setResourceType(resourceDTO.getResourceType());
        resource.setResourceQuantity(resourceDTO.getResourceQuantity());
        resource.setResourceStatus(resourceDTO.getResourceStatus());
        resource.setWarehouseId(resourceDTO.getWarehouseId());

        resourceRepository.save(resource);
    }

    public void deleteResource(Long id) {
        if (resourceRepository.existsById(id)) {
            resourceRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Resource with id " + id + " does not exist");
        }
    }
}