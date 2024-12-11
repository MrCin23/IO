package pl.lodz.p.ias.io.darczyncy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.lodz.p.ias.io.darczyncy.repositories.FinancialDonationRepository;
import pl.lodz.p.ias.io.darczyncy.services.implementations.FinancialDonationService;

import pl.lodz.p.ias.io.uwierzytelnianie.repositories.UserRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;

@SpringBootTest(classes = {AuthenticationService.class})
public class FinancialDonationTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FinancialDonationService financialDonationService;

    @Autowired
    private FinancialDonationRepository financialDonationRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        financialDonationRepository.deleteAll();
    }

    @Test
    public void createFinancialDonation() {

    }



}
