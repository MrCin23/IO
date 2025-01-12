package pl.lodz.p.ias.io.zasoby.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.uwierzytelnianie.enums.UserRole;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;
import pl.lodz.p.ias.io.zasoby.dto.ResourceDTO;
import pl.lodz.p.ias.io.zasoby.service.ResourceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;
    private final AuthenticationService authenticationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceDTO addResource(@RequestBody @Valid ResourceDTO resourceDTO) {
        return resourceService.addResource(resourceDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResourceDTO getResource(@PathVariable UUID id) {
        return resourceService.getResourceById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResourceDTO> getAllResources() {
        return resourceService.getAllResources();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateResource(@PathVariable UUID id, @RequestBody ResourceDTO resourceDTO) {
        resourceService.updateResource(id, resourceDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResource(@PathVariable UUID id) {
        resourceService.deleteResource(id);
    }
}