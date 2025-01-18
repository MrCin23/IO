package pl.lodz.p.ias.io.zasoby.controller;

import io.swagger.v3.oas.annotations.headers.Header;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.powiadomienia.Interfaces.INotificationService;
import pl.lodz.p.ias.io.powiadomienia.notification.NotificationType;
import pl.lodz.p.ias.io.zasoby.dto.ResourceDTO;
import pl.lodz.p.ias.io.zasoby.service.ResourceService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;
    private final INotificationService notificationService;

    @PreAuthorize("hasAnyRole('DARCZYŃCA', 'PRZEDSTAWICIEL_WŁADZ', 'ORGANIZACJA_POMOCOWA')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceDTO addResource(@RequestBody @Valid ResourceDTO resourceDTO,
                                   @RequestHeader(value = "message") String message) {
        message = URLDecoder.decode(message, StandardCharsets.UTF_8);
        notificationService.notify(message, NotificationType.INFORMATION);
        return resourceService.addResource(resourceDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResourceDTO getResource(@PathVariable Long id) {
        return resourceService.getResourceById(id);
    }

    @PreAuthorize("hasAnyRole('DARCZYŃCA', 'PRZEDSTAWICIEL_WŁADZ', 'ORGANIZACJA_POMOCOWA', 'POSZKODOWANY', 'WOLONTARIUSZ')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResourceDTO> getAllResources() {
        return resourceService.getAllResources();
    }

    @PreAuthorize("hasAnyRole('PRZEDSTAWICIEL_WŁADZ', 'ORGANIZACJA_POMOCOWA')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateResource(@PathVariable Long id, @RequestBody ResourceDTO resourceDTO) {
        resourceService.updateResource(id, resourceDTO);
    }

    @PreAuthorize("hasAnyRole('PRZEDSTAWICIEL_WŁADZ', 'ORGANIZACJA_POMOCOWA')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResource(@PathVariable Long id,
                               @RequestHeader(value = "message") String message) {
        message = URLDecoder.decode(message, StandardCharsets.UTF_8);
        notificationService.notify(message, NotificationType.INFORMATION);
        resourceService.deleteResource(id);
    }
}