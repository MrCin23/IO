package pl.lodz.p.ias.io.poszkodowani.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.mapy.model.MapPoint;
import pl.lodz.p.ias.io.mapy.service.MapService;
import pl.lodz.p.ias.io.poszkodowani.dto.financialneed.FinancialNeedCreateRequest;
import pl.lodz.p.ias.io.poszkodowani.dto.financialneed.FinancialNeedResponse;
import pl.lodz.p.ias.io.poszkodowani.mapper.FinancialNeedMapper;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.poszkodowani.model.Need;
import pl.lodz.p.ias.io.poszkodowani.service.FinancialNeedService;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;

import java.util.List;
import java.util.Optional;

@CrossOrigin("localhost:5173")
@RestController
@RequestMapping("/api/financial-needs")
@Validated
public class FinancialNeedController {

    private final FinancialNeedService financialNeedService;
    private final AuthenticationService authenticationService;
    private final FinancialNeedMapper financialNeedMapper;
    private final MapService mapService;

    @Autowired
    public FinancialNeedController(FinancialNeedService financialNeedService, FinancialNeedMapper financialNeedMapper, AuthenticationService authenticationService, MapService mapService) {
        this.financialNeedService = financialNeedService;
        this.financialNeedMapper = financialNeedMapper;
        this.authenticationService = authenticationService;
        this.mapService = mapService;
    }

    @PostMapping
    public ResponseEntity<FinancialNeedResponse> createFinancialNeed(@Valid @RequestBody FinancialNeedCreateRequest dto) {
        FinancialNeed financialNeed = financialNeedMapper.toFinancialNeed(dto);
        Account user = authenticationService.getAccountById(dto.getUserId());
        MapPoint mapPoint = mapService.getPoint(dto.getMapPointId());
        financialNeed.setUser(user);
        financialNeed.setMapPoint(mapPoint);
        FinancialNeed savedFinancialNeed = financialNeedService.createFinancialNeed(financialNeed);
        FinancialNeedResponse responseDTO = financialNeedMapper.toFinancialNeedResponse(savedFinancialNeed);
        return ResponseEntity.ok(responseDTO);
    }


    @GetMapping("/{id}")
    public ResponseEntity<FinancialNeedResponse> getFinancialNeedById(@PathVariable Long id) {
        return financialNeedService.getFinancialNeedById(id)
                .map(financialNeed -> ResponseEntity.ok(financialNeedMapper.toFinancialNeedResponse(financialNeed)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<FinancialNeedResponse>> getAllFinancialNeeds() {
        List<FinancialNeed> financialNeeds = financialNeedService.getAllFinancialNeeds();
        List<FinancialNeedResponse> responseDTOs = financialNeedMapper.toFinancialNeedResponseList(financialNeeds);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FinancialNeedResponse>> getMaterialNeedsByUserId(@PathVariable Long userId) {
        List<FinancialNeed> financialNeeds = financialNeedService.getFinancialNeedByUserId(userId);
        List<FinancialNeedResponse> responseDTOs = financialNeedMapper.toFinancialNeedResponseList(financialNeeds);
        return ResponseEntity.ok(responseDTOs);
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<FinancialNeedResponse> changeStatus(@PathVariable Long id, @RequestParam Need.Status status) {
        Optional<FinancialNeed> optionalFinancialNeed = financialNeedService.changeStatus(id, status);
        return optionalFinancialNeed
                .map(financialNeed -> ResponseEntity.ok(financialNeedMapper.toFinancialNeedResponse(financialNeed)))
                .orElse(ResponseEntity.notFound().build());
    }
}
