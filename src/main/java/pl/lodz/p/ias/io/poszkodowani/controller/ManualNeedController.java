package pl.lodz.p.ias.io.poszkodowani.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.mapy.model.MapPoint;
import pl.lodz.p.ias.io.mapy.service.MapService;
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

@CrossOrigin("localhost:5173")
@RestController
@RequestMapping("/api/manual-needs")
@Validated
public class ManualNeedController {

    private final ManualNeedService manualNeedService;
    private final ManualNeedMapper manualNeedMapper;
    private final AuthenticationService authenticationService;
    private final MapService mapService;

    @Autowired
    public ManualNeedController(ManualNeedService manualNeedService, ManualNeedMapper manualNeedMapper, AuthenticationService authenticationService, MapService mapService) {
        this.manualNeedService = manualNeedService;
        this.manualNeedMapper = manualNeedMapper;
        this.authenticationService = authenticationService;
        this.mapService = mapService;
    }

    @PostMapping
    public ResponseEntity<ManualNeedResponse> createManualNeed(@Valid @RequestBody ManualNeedCreateRequest dto) {
        ManualNeed manualNeed = manualNeedMapper.toManualNeed(dto);
        Account user = authenticationService.getAccountById(dto.getUserId());
        MapPoint mapPoint = mapService.getPoint(dto.getMapPointId());
        manualNeed.setUser(user);
        manualNeed.setMapPoint(mapPoint);
        ManualNeed savedManualNeed = manualNeedService.createManualNeed(manualNeed);
        ManualNeedResponse responseDTO = manualNeedMapper.toManualNeedResponse(savedManualNeed);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManualNeedResponse> getManualNeedById(@PathVariable Long id) {
        return manualNeedService.getManualNeedById(id)
                .map(manualNeed -> ResponseEntity.ok(manualNeedMapper.toManualNeedResponse(manualNeed)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ManualNeedResponse>> getAllManualNeeds() {
        List<ManualNeed> manualNeeds = manualNeedService.getAllManualNeeds();
        List<ManualNeedResponse> responseDTOs = manualNeedMapper.toManualNeedResponseList(manualNeeds);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ManualNeedResponse>> getManualNeedsByUserId(@PathVariable Long userId) {
        List<ManualNeed> manualNeeds = manualNeedService.getManualNeedsByUserId(userId);
        List<ManualNeedResponse> responseDTOs = manualNeedMapper.toManualNeedResponseList(manualNeeds);
        return ResponseEntity.ok(responseDTOs);
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<ManualNeedResponse> changeStatus(@PathVariable Long id, @RequestParam Need.Status status) {
        Optional<ManualNeed> manualNeed = manualNeedService.changeStatus(id, status);
        return manualNeed
                .map(value -> ResponseEntity.ok(manualNeedMapper.toManualNeedResponse(value)))
                .orElse(ResponseEntity.notFound().build());
    }

}
