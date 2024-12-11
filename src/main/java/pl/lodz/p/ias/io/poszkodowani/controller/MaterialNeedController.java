package pl.lodz.p.ias.io.poszkodowani.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.poszkodowani.dto.materialneed.MaterialNeedCreateRequestDTO;
import pl.lodz.p.ias.io.poszkodowani.dto.materialneed.MaterialNeedResponseDTO;
import pl.lodz.p.ias.io.poszkodowani.mapper.MaterialNeedMapper;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;
import pl.lodz.p.ias.io.poszkodowani.service.MaterialNeedService;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/material-needs")
@Validated
public class MaterialNeedController {

    private final MaterialNeedService materialNeedService;
    private final MaterialNeedMapper materialNeedMapper;
    private final AuthenticationService authenticationService;

    @Autowired
    public MaterialNeedController(MaterialNeedService materialNeedService, MaterialNeedMapper materialNeedMapper, AuthenticationService authenticationService) {
        this.materialNeedService = materialNeedService;
        this.materialNeedMapper = materialNeedMapper;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<MaterialNeedResponseDTO> createMaterialNeed(@Valid @RequestBody MaterialNeedCreateRequestDTO dto) {
        MaterialNeed materialNeed = materialNeedMapper.toMaterialNeed(dto);
        Optional<Account> user = authenticationService.getAccountById(dto.getUserId());
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        materialNeed.setUser(user.get());
        MaterialNeed savedMaterialNeed = materialNeedService.createMaterialNeed(materialNeed);
        MaterialNeedResponseDTO responseDTO = materialNeedMapper.toMaterialNeedResponseDTO(savedMaterialNeed);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialNeedResponseDTO> getMaterialNeedById(@PathVariable Long id) {
        return materialNeedService.getMaterialNeedById(id)
                .map(materialNeed -> ResponseEntity.ok(materialNeedMapper.toMaterialNeedResponseDTO(materialNeed)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MaterialNeedResponseDTO>> getAllMaterialNeeds() {
        List<MaterialNeed> materialNeeds = materialNeedService.getAllMaterialNeeds();
        List<MaterialNeedResponseDTO> responseDTOs = materialNeedMapper.toMaterialNeedResponseDTOList(materialNeeds);
        return ResponseEntity.ok(responseDTOs);
    }


}
