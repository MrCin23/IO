package pl.lodz.p.ias.io.poszkodowani.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.mapy.model.MapPoint;
import pl.lodz.p.ias.io.mapy.service.MapService;
import pl.lodz.p.ias.io.poszkodowani.dto.manualneed.ManualNeedResponse;
import pl.lodz.p.ias.io.poszkodowani.dto.materialneed.MaterialNeedCreateRequest;
import pl.lodz.p.ias.io.poszkodowani.dto.materialneed.MaterialNeedResponse;
import pl.lodz.p.ias.io.poszkodowani.mapper.MaterialNeedMapper;
import pl.lodz.p.ias.io.poszkodowani.model.ManualNeed;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;
import pl.lodz.p.ias.io.poszkodowani.model.Need;
import pl.lodz.p.ias.io.poszkodowani.service.MaterialNeedService;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;

import java.util.List;
import java.util.Optional;

@PreAuthorize("isAuthenticated()")
@CrossOrigin("localhost:5173")
@RestController
@RequestMapping("/api/material-needs")
@Validated
public class MaterialNeedController {

    private final MaterialNeedService materialNeedService;
    private final MaterialNeedMapper materialNeedMapper;
    private final AuthenticationService authenticationService;
    private final MapService mapService;

    @Autowired
    public MaterialNeedController(MaterialNeedService materialNeedService, MaterialNeedMapper materialNeedMapper, AuthenticationService authenticationService, MapService mapService) {
        this.materialNeedService = materialNeedService;
        this.materialNeedMapper = materialNeedMapper;
        this.authenticationService = authenticationService;
        this.mapService = mapService;
    }

    @PostMapping
    public ResponseEntity<MaterialNeedResponse> createMaterialNeed(@Valid @RequestBody MaterialNeedCreateRequest dto) {
        MaterialNeed materialNeed = materialNeedMapper.toMaterialNeed(dto);
        Account user = authenticationService.getAccountById(dto.getUserId());
        MapPoint mapPoint = mapService.getPoint(dto.getMapPointId());
        materialNeed.setUser(user);
        materialNeed.setMapPoint(mapPoint);
        MaterialNeed savedMaterialNeed = materialNeedService.createMaterialNeed(materialNeed);
        MaterialNeedResponse responseDTO = materialNeedMapper.toMaterialNeedResponse(savedMaterialNeed);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialNeedResponse> getMaterialNeedById(@PathVariable Long id) {
        return materialNeedService.getMaterialNeedById(id)
                .map(materialNeed -> ResponseEntity.ok(materialNeedMapper.toMaterialNeedResponse(materialNeed)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MaterialNeedResponse>> getAllMaterialNeeds() {
        List<MaterialNeed> materialNeeds = materialNeedService.getAllMaterialNeeds();
        List<MaterialNeedResponse> responseDTOs = materialNeedMapper.toMaterialNeedResponseList(materialNeeds);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MaterialNeedResponse>> getMaterialNeedsByUserId(@PathVariable Long userId) {
        List<MaterialNeed> materialNeeds = materialNeedService.getMaterialNeedsByUserId(userId);
        List<MaterialNeedResponse> responseDTOs = materialNeedMapper.toMaterialNeedResponseList(materialNeeds);
        return ResponseEntity.ok(responseDTOs);
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<MaterialNeedResponse> changeStatus(@PathVariable Long id, @RequestParam Need.Status status) {
        Optional<MaterialNeed> materialNeed = materialNeedService.changeStatus(id, status);
        return materialNeed
                .map(value -> ResponseEntity.ok(materialNeedMapper.toMaterialNeedResponse(value)))
                .orElse(ResponseEntity.notFound().build());
    }


}
