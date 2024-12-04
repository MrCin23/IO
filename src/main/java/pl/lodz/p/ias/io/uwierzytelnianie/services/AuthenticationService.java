package pl.lodz.p.ias.io.uwierzytelnianie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Role;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.RoleRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.model.User;
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

    public User register(String username, String password, String firstName, String lastName, String roleName) {
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists!");
        }

        Role role = roleRepository.findByRoleName(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Invalid role name!");
        }

        String passwordHash = passwordEncoder.encode(password);
        User newUser = new User(username, passwordHash, role, firstName, lastName);
        return userRepository.save(newUser);
    }
}
