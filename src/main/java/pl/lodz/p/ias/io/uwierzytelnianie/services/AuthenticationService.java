package pl.lodz.p.ias.io.uwierzytelnianie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.uwierzytelnianie.exceptions.BadRequestException;
import pl.lodz.p.ias.io.uwierzytelnianie.exceptions.ConflictException;
import pl.lodz.p.ias.io.uwierzytelnianie.exceptions.NotFoundException;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Role;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.RoleRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.AccountRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.utils.JwtTokenUtil;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthenticationService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(AccountRepository accountRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil, CustomUserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    public Account register(String username, String password, String firstName, String lastName, String roleName) {
        Role role = roleRepository.findByRoleName(roleName);
        if (role == null) {
            throw new NotFoundException("Invalid role name!");
        }

        String passwordHash = passwordEncoder.encode(password);

        Account newUser = new Account(username, passwordHash, role, firstName, lastName);

        try {
            return accountRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("account_username_key")) {
                throw new ConflictException("Username already exists!");
            } else if (e.getMessage().contains("account_email_key")) {
                throw new ConflictException("Email already exists!");
            } else {
                throw new ConflictException("A conflict occurred during registration!");
            }
        }
    }

    public String login(String username, String password) {
        Account account = accountRepository.findByUsername(username);

        if (account == null) {
            throw new BadRequestException("Wrong credentials");
        }

        if (passwordEncoder.matches(password, account.getPasswordHash())) {
            account.setLastLogin(LocalDateTime.now());
            accountRepository.save(account);
        } else {
            throw new BadRequestException("Wrong credentials");
        }

        if (!account.isActive()) {
            throw new ConflictException("User is not active");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenUtil.generateToken(
                username,
                userDetailsService.loadUserByUsername(username).getAuthorities().iterator().next().getAuthority()
        );
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found!"));

        if (account == null) {
            throw new NotFoundException("Account not found!");
        }

        return account;
    }

    public Account getAccountByUsername(String username) {
        Account account = accountRepository.findByUsername(username);

        if (account == null) {
            throw new NotFoundException("Account not found!");
        }

        return account;
    }

    public List<Account> getAccountsByRole(String roleName) {
        Role role = roleRepository.findByRoleName(roleName);

        return accountRepository.findByRole(role);
    }

    public Account resetPassword(String username, String password) {
        Account account = accountRepository.findByUsername(username);

        if (account == null) {
            throw new NotFoundException("Account not found!");
        }

        account.setPasswordHash(passwordEncoder.encode(password));

        return accountRepository.save(account);
    }

    public List<Account> getAccountsById(List<Long> ids) {
        return accountRepository.findAllById(ids);
    }

    public Account activateAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found!"));

        if (account == null) {
            throw new NotFoundException("Account not found!");
        }

        account.setActive(true);

        return accountRepository.save(account);
    }

    public Account deactivateAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found!"));

        if (account == null) {
            throw new NotFoundException("Account not found!");
        }

        account.setActive(false);

        return accountRepository.save(account);
    }
}
