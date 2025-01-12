package pl.lodz.p.ias.io.poszkodowani.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.poszkodowani.dto.manualneed.ManualNeedCreateRequest;
import pl.lodz.p.ias.io.poszkodowani.dto.manualneed.ManualNeedResponse;
import pl.lodz.p.ias.io.poszkodowani.mapper.ManualNeedMapper;
import pl.lodz.p.ias.io.poszkodowani.model.ManualNeed;
import pl.lodz.p.ias.io.poszkodowani.model.Need;
import pl.lodz.p.ias.io.poszkodowani.service.ManualNeedService;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/manual-needs")
@Validated
public class ManualNeedController {

    private final ManualNeedService materialNeedService;
    private final ManualNeedMapper materialNeedMapper;
    private final AuthenticationService authenticationService;

    @Autowired
    public ManualNeedController(ManualNeedService materialNeedService, ManualNeedMapper materialNeedMapper, AuthenticationService authenticationService) {
        this.materialNeedService = materialNeedService;
        this.materialNeedMapper = materialNeedMapper;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<ManualNeedResponse> createManualNeed(@Valid @RequestBody ManualNeedCreateRequest dto) {
        ManualNeed manualNeed = materialNeedMapper.toManualNeed(dto);
        Optional<Account> user = authenticationService.getAccountById(dto.getUserId());
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        manualNeed.setUser(user.get());
        ManualNeed savedManualNeed = materialNeedService.createManualNeed(manualNeed);
        ManualNeedResponse responseDTO = materialNeedMapper.toManualNeedResponse(savedManualNeed);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManualNeedResponse> getManualNeedById(@PathVariable Long id) {
        return materialNeedService.getManualNeedById(id)
                .map(manualNeed -> ResponseEntity.ok(materialNeedMapper.toManualNeedResponse(manualNeed)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ManualNeedResponse>> getAllManualNeeds() {
        List<ManualNeed> manualNeeds = materialNeedService.getAllManualNeeds();
        List<ManualNeedResponse> responseDTOs = materialNeedMapper.toManualNeedResponseList(manualNeeds);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ManualNeedResponse>> getManualNeedsByUserId(@PathVariable Long userId) {
        List<ManualNeed> manualNeeds = materialNeedService.getManualNeedsByUserId(userId);
        List<ManualNeedResponse> responseDTOs = materialNeedMapper.toManualNeedResponseList(manualNeeds);
        return ResponseEntity.ok(responseDTOs);
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<ManualNeedResponse> changeStatus(@PathVariable Long id, @RequestParam Need.Status newStatus) {
        Optional<ManualNeed> manualNeed = materialNeedService.changeStatus(id, newStatus);
        return manualNeed
                .map(value -> ResponseEntity.ok(materialNeedMapper.toManualNeedResponse(value)))
                .orElse(ResponseEntity.notFound().build());
    }

}
