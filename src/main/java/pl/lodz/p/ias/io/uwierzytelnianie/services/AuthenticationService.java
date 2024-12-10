package pl.lodz.p.ias.io.uwierzytelnianie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Role;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.RoleRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.UserRepository;

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
        return userRepository.save(newUser);
    }
}
