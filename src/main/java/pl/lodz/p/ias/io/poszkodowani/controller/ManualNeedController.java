package pl.lodz.p.ias.io.poszkodowani.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.poszkodowani.dto.manualneed.ManualNeedCreateRequestDTO;
import pl.lodz.p.ias.io.poszkodowani.dto.manualneed.ManualNeedResponseDTO;
import pl.lodz.p.ias.io.poszkodowani.dto.materialneed.MaterialNeedCreateRequestDTO;
import pl.lodz.p.ias.io.poszkodowani.mapper.ManualNeedMapper;
import pl.lodz.p.ias.io.poszkodowani.mapper.MaterialNeedMapper;
import pl.lodz.p.ias.io.poszkodowani.model.ManualNeed;
import pl.lodz.p.ias.io.poszkodowani.service.ManualNeedService;
import pl.lodz.p.ias.io.poszkodowani.service.MaterialNeedService;
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
    public ResponseEntity<ManualNeedResponseDTO> createManualNeed(@Valid @RequestBody ManualNeedCreateRequestDTO dto) {
        ManualNeed manualNeed = materialNeedMapper.toManualNeed(dto);
        Optional<Account> user = authenticationService.getAccountById(dto.getUserId());
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        manualNeed.setUser(user.get());
        ManualNeed savedManualNeed = materialNeedService.createManualNeed(manualNeed);
        ManualNeedResponseDTO responseDTO = materialNeedMapper.toManualNeedResponseDTO(savedManualNeed);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManualNeedResponseDTO> getManualNeedById(@PathVariable Long id) {
        return materialNeedService.getManualNeedById(id)
                .map(manualNeed -> ResponseEntity.ok(materialNeedMapper.toManualNeedResponseDTO(manualNeed)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ManualNeedResponseDTO>> getAllManualNeeds() {
        List<ManualNeed> manualNeeds = materialNeedService.getAllManualNeeds();
        List<ManualNeedResponseDTO> responseDTOs = materialNeedMapper.toManualNeedResponseDTOList(manualNeeds);
        return ResponseEntity.ok(responseDTOs);
    }

}
