package pl.lodz.p.ias.io.uwierzytelnianie.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Role;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByRoleName("DARCZYŃCA") == null) {
            Role adminRole = new Role("DARCZYŃCA");
            roleRepository.save(adminRole);
        }

        if (roleRepository.findByRoleName("POSZKODOWANY") == null) {
            Role userRole = new Role("POSZKODOWANY");
            roleRepository.save(userRole);
        }

        if (roleRepository.findByRoleName("ORGANIZACJA_POMOCOWA") == null) {
            Role userRole = new Role("ORGANIZACJA_POMOCOWA");
            roleRepository.save(userRole);
        }

        if (roleRepository.findByRoleName("WOLONTARIUSZ") == null) {
            Role userRole = new Role("WOLONTARIUSZ");
            roleRepository.save(userRole);
        }

        if (roleRepository.findByRoleName("PRZEDSTAWICIEL_WŁADZ") == null) {
            Role userRole = new Role("PRZEDSTAWICIEL_WŁADZ");
            roleRepository.save(userRole);
        }
    }
}

