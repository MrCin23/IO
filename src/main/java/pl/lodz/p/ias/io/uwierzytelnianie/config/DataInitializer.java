package pl.lodz.p.ias.io.uwierzytelnianie.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.lodz.p.ias.io.uwierzytelnianie.enums.UserRole;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Role;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.AccountRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.RoleRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final AuthenticationService authenticationService;

    public DataInitializer(RoleRepository roleRepository, AuthenticationService authenticationService) {
        this.roleRepository = roleRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public void run(String... args) throws Exception {
        for (UserRole role : UserRole.values()) {
            roleRepository.save(new Role(role.name()));
        }

        authenticationService.register("darczynca", "darczynca", "Jan", "Kowalski", "DARCZYŃCA");
        authenticationService.register("poszkodowany", "poszkodowany", "Jane", "Doe", "POSZKODOWANY");
        authenticationService.register("organizacja", "organizacja", "John", "Doe", "ORGANIZACJA_POMOCOWA");
        authenticationService.register("wolontariusz", "wolontariusz", "Anna", "Kowalska", "WOLONTARIUSZ");
        authenticationService.register("przedstawiciel", "przedstawiciel", "Jan", "Nowak", "PRZEDSTAWICIEL_WŁADZ");
    }
}


