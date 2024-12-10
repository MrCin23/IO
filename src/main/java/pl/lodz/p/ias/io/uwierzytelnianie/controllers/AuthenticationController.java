package pl.lodz.p.ias.io.uwierzytelnianie.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.uwierzytelnianie.DTO.AccountCreateDTO;
import pl.lodz.p.ias.io.uwierzytelnianie.enums.UserRole;
import pl.lodz.p.ias.io.uwierzytelnianie.mappers.AccountMapper;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;
import pl.lodz.p.ias.io.uwierzytelnianie.utils.EnumUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
//    private final AccountMapper userMapper = new AccountMapper();

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid AccountCreateDTO request) {
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

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts() {
        List<Account> accounts = authenticationService.getAccounts();
       /* List<Account> accountGetDTOs =  accounts.stream()
                .map(accountMapper::userToUserGetDTO)
                .toList();*/
        return ResponseEntity.ok(accounts);
    }
}
