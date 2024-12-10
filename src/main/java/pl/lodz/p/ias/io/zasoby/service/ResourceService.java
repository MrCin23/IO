package pl.lodz.p.ias.io.zasoby.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.zasoby.dto.ResourceDTO;
import pl.lodz.p.ias.io.zasoby.model.Resource;
import pl.lodz.p.ias.io.zasoby.repository.ResourceRepository;
import pl.lodz.p.ias.io.zasoby.utils.ResourceConverter;

@Service
public class ResourceService {
    private final ResourceRepository resourceRepository;
    private final ResourceConverter converter = new ResourceConverter();

    @Autowired
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public ResourceDTO addResource(ResourceDTO resourceDTO) {
        Resource resource = converter.convertDTOToResource(resourceDTO);
        resourceRepository.create(resource);
        return converter.convertResourceToDTO(resource);
    }
}