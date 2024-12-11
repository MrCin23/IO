package pl.lodz.p.ias.io.poszkodowani.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.poszkodowani.dto.financialneed.FinancialNeedCreateRequestDTO;
import pl.lodz.p.ias.io.poszkodowani.dto.financialneed.FinancialNeedResponseDTO;
import pl.lodz.p.ias.io.poszkodowani.mapper.FinancialNeedMapper;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.poszkodowani.service.FinancialNeedService;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/financial-needs")
@Validated
public class FinancialNeedController {

    private final FinancialNeedService financialNeedService;
    private final AuthenticationService authenticationService;
    private final FinancialNeedMapper financialNeedMapper;

    @Autowired
    public FinancialNeedController(FinancialNeedService financialNeedService, FinancialNeedMapper financialNeedMapper, AuthenticationService authenticationService) {
        this.financialNeedService = financialNeedService;
        this.financialNeedMapper = financialNeedMapper;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<FinancialNeedResponseDTO> createFinancialNeed(@Valid @RequestBody FinancialNeedCreateRequestDTO dto) {
        FinancialNeed financialNeed = financialNeedMapper.toFinancialNeed(dto);
        Optional<Account> user = authenticationService.getAccountById(dto.getUserId());
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        financialNeed.setUser(user.get());
        FinancialNeed savedFinancialNeed = financialNeedService.createFinancialNeed(financialNeed);
        FinancialNeedResponseDTO responseDTO = financialNeedMapper.toFinancialNeedResponseDTO(savedFinancialNeed);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinancialNeedResponseDTO> getFinancialNeedById(@PathVariable Long id) {
        return financialNeedService.getFinancialNeedById(id)
                .map(financialNeed -> ResponseEntity.ok(financialNeedMapper.toFinancialNeedResponseDTO(financialNeed)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<FinancialNeedResponseDTO>> getAllFinancialNeeds() {
        List<FinancialNeed> financialNeeds = financialNeedService.getAllFinancialNeeds();
        List<FinancialNeedResponseDTO> responseDTOs = financialNeedMapper.toFinancialNeedResponseDTOList(financialNeeds);
        return ResponseEntity.ok(responseDTOs);
    }
}
