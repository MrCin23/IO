package pl.lodz.p.ias.io.zasoby.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.zasoby.dto.ResourceDTO;
import pl.lodz.p.ias.io.zasoby.service.ResourceService;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;

//    @Autowired
//    public ResourceController(ResourceService resourceService) {
//        this.resourceService = resourceService;
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceDTO addResource(@RequestBody @Valid ResourceDTO resourceDTO) {
//        ResourceDTO resource = resourceService.addResource(resourceDTO);
//        return ResponseEntity.ok(resource);
//        return ResponseEntity.ok(resourceService.addResource(resourceDTO));
        return resourceService.addResource(resourceDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResourceDTO getResource(@PathVariable long id) {
        return resourceService.getResourceById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResourceDTO> getAllResources() {
        return resourceService.getAllResources();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateResource(@PathVariable long id, @RequestBody ResourceDTO resourceDTO) {
        resourceService.updateResource(id, resourceDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResource(@PathVariable long id) {
        resourceService.deleteResource(id);
    }
}