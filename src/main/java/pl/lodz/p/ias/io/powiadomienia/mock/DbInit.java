package pl.lodz.p.ias.io.powiadomienia.mock;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class DbInit {

    private final MockUserRepo userRepo;

    @Bean
    public int dbInitttttt() {
        MockUser user = new MockUser();
        user.setUsername("Tomek");
        userRepo.save(user);
        return 1;
    }
}
