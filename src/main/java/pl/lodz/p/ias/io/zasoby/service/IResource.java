package pl.lodz.p.ias.io.zasoby.service;

import jakarta.validation.Valid;
import pl.lodz.p.ias.io.zasoby.dto.ResourceDTO;

import java.util.List;

public interface IResource {

    ResourceDTO addResource(ResourceDTO resourceDTO);

    ResourceDTO getResourceById(Long id);

    List<ResourceDTO> getAllResources();

    void updateResource(Long id, @Valid ResourceDTO resourceDTO);

    void deleteResource(Long id);
}
