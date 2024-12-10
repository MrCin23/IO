package pl.lodz.p.ias.io.zasoby.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.ias.io.zasoby.dto.ResourceDTO;
import pl.lodz.p.ias.io.zasoby.service.ResourceService;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping("/")
    public ResponseEntity<ResourceDTO> addResource(@RequestBody ResourceDTO resourceDTO) {
        ResourceDTO resource = resourceService.addResource(resourceDTO);
        return ResponseEntity.ok(resource);
    }
}
