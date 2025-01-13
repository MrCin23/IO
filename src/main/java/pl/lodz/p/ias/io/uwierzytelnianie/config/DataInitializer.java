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
        roleRepository.save(new Role("ROLE_DARCZYŃCA"));
        roleRepository.save(new Role("ROLE_POSZKODOWANY"));
        roleRepository.save(new Role("ROLE_ORGANIZACJA_POMOCOWA"));
        roleRepository.save(new Role("ROLE_WOLONTARIUSZ"));
        roleRepository.save(new Role("ROLE_PRZEDSTAWICIEL_WŁADZ"));
    }
}

