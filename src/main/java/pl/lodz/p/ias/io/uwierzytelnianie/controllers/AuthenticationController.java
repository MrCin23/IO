package pl.lodz.p.ias.io.uwierzytelnianie.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.uwierzytelnianie.DTO.UserCreateDTO;
import pl.lodz.p.ias.io.uwierzytelnianie.enums.UserRole;
import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;
import pl.lodz.p.ias.io.uwierzytelnianie.utils.EnumUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserCreateDTO request) {
        try {
            if (!EnumUtils.isValidEnum(UserRole.class, request.getRoleName().toUpperCase())) {
                String availableRoles = Arrays.stream(UserRole.values())
                        .map(Enum::name)
                        .collect(Collectors.joining(", "));
                return ResponseEntity.badRequest().body("Invalid role name! Available roles are: " + availableRoles);
            }

            authenticationService.register(
                    request.getUsername(),
                    request.getPassword(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getRoleName()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
