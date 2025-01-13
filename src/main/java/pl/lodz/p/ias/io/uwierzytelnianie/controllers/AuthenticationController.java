package pl.lodz.p.ias.io.uwierzytelnianie.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.uwierzytelnianie.DTO.AccountCreateDTO;
import pl.lodz.p.ias.io.uwierzytelnianie.DTO.AccountLoginDTO;
import pl.lodz.p.ias.io.uwierzytelnianie.DTO.AccountResetPassDTO;
import pl.lodz.p.ias.io.uwierzytelnianie.enums.UserRole;
import pl.lodz.p.ias.io.uwierzytelnianie.exceptions.BadRequestException;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;
import pl.lodz.p.ias.io.uwierzytelnianie.utils.EnumUtils;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody @Valid AccountCreateDTO request) {
        if (!EnumUtils.isValidEnum(UserRole.class, request.getRoleName().toUpperCase())) {
            throw new BadRequestException("Invalid role name! Available roles are: " + EnumUtils.availableRoles());
        }

        Account account = authenticationService.register(
                request.getUsername(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName(),
                request.getRoleName()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AccountLoginDTO request) {
        return ResponseEntity.ok(authenticationService.login(request.getUsername(), request.getPassword()));
    }

//    @PreAuthorize("hasAnyRole('DARCZYŃCA', 'WOLONTARIUSZ')")
    @GetMapping
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity.ok(authenticationService.getAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(authenticationService.getAccountById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Account> getAccountById(@PathVariable String username) {
        return ResponseEntity.ok(authenticationService.getAccountByUsername(username));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<Account>> getAccounts(@PathVariable String role) {
        if (!EnumUtils.isValidEnum(UserRole.class, role.toUpperCase())) {
            throw new BadRequestException("Invalid role name! Available roles are: " + EnumUtils.availableRoles());
        }

        List<Account> accounts = authenticationService.getAccountsByRole(role);

        return ResponseEntity.ok(accounts);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reset")
    public ResponseEntity<Account> resetPassword(@RequestBody @Valid AccountResetPassDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return ResponseEntity.ok(authenticationService.resetPassword(username, request.getPassword()));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<Account> activateAccount(@PathVariable Long id) {
        return ResponseEntity.ok(authenticationService.activateAccount(id));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<Account> deactivateAccount(@PathVariable Long id) {
        return ResponseEntity.ok(authenticationService.deactivateAccount(id));
    }
}
