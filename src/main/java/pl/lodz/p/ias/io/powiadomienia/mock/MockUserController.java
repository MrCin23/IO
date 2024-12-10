package pl.lodz.p.ias.io.powiadomienia.mock;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MockUserController {
    MockUserRepo userRepo;

    @GetMapping("/powiadomienia/mockUsers")
    public List<MockUser> getUsers() {
        return userRepo.findAll();
    }

}
