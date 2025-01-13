package pl.lodz.p.ias.io.darczyncy.controller.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.ias.io.darczyncy.dto.create.CreateDonorDTO;
import pl.lodz.p.ias.io.uwierzytelnianie.enums.UserRole;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class DonorController {

    AuthenticationService authenticationService;

    public ResponseEntity<?> createDonor(CreateDonorDTO createDonorDTO) {
        Account donor = authenticationService.register(
                createDonorDTO.userName(),
                createDonorDTO.password(),
                createDonorDTO.firstName(),
                createDonorDTO.lastName(),
                String.valueOf(UserRole.ROLE_DARCZYŃCA));
        return ResponseEntity.ok(URI.create("/api/donors/%s".formatted(donor.getId())));
    }

}
