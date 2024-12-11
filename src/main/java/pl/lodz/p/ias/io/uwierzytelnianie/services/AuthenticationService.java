package pl.lodz.p.ias.io.uwierzytelnianie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Role;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.RoleRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account register(String username, String password, String firstName, String lastName, String roleName) {
        Role role = roleRepository.findByRoleName(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Invalid role name!");
        }

        String passwordHash = passwordEncoder.encode(password);

        Account newUser = new Account(username, passwordHash, role, firstName, lastName);

        try {
            return userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Username already exists!");
        }
    }

    public Boolean login(String username, String password) {
        Account account = userRepository.findByUsername(username);
        return passwordEncoder.matches(password, account.getPasswordHash());
    }

    public List<Account> getAccounts() {
        return userRepository.findAll();
    }

    public Optional<Account> getAccountById(Long id) {
        return userRepository.findById(id);
    }

    public Account getAccountByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Account> getAccountsByRole(String roleName) {
        Role role = roleRepository.findByRoleName(roleName);

        return userRepository.findByRole(role);
    }

    public Account resetPassword(String username, String password) {
        Account account = userRepository.findByUsername(username);
        account.setPasswordHash(passwordEncoder.encode(password));

        return userRepository.save(account);
    }

    public List<Account> getAccountsById(List<Long> ids) {
        return userRepository.findAllById(ids);
    }
}
