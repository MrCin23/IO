package pl.lodz.p.ias.io.darczyncy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import pl.lodz.p.ias.io.darczyncy.dto.create.FinancialDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;
import pl.lodz.p.ias.io.darczyncy.repositories.FinancialDonationRepository;
import pl.lodz.p.ias.io.darczyncy.services.implementations.FinancialDonationService;

import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.poszkodowani.model.Need;
import pl.lodz.p.ias.io.poszkodowani.repository.FinancialNeedRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Role;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.AccountRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.RoleRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;
import pl.lodz.p.ias.io.zasoby.model.Warehouse;
import pl.lodz.p.ias.io.zasoby.repository.WarehouseRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@SpringBootTest
@ComponentScan(basePackages = "pl.lodz.p.ias.io.uwierzytelnianie.repositories")
public class FinancialDonationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FinancialDonationService financialDonationService;

    @Autowired
    private FinancialDonationRepository financialDonationRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    FinancialNeedRepository financialNeedRepository;

    @BeforeEach
    public void setUp() {
        financialDonationRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void createFinancialDonation() {

        Role role = new Role("DARCZYNCA");
        roleRepository.save(role);
        Account newUser = accountRepository.save(new Account(
                "User",
                "Password",
                role,
                "Jan",
                "Nowak"
        ));
        authenticationService.login("User", "Password");

        FinancialNeed financialNeed = FinancialNeed.builder()
                .collectionGoal(200)
                .collectionStatus(2)
                .description("aa")
                .mapPointId(2L)
                .expirationDate(Date.from(Instant.now().plus(2, ChronoUnit.DAYS)))
                .status(Need.Status.PENDING)
                .priority(2)
                .creationDate(Date.from(Instant.now().minus(1, ChronoUnit.DAYS)))
                .user(newUser)
        .build();
        financialNeedRepository.save(financialNeed);

        Warehouse warehouse = new Warehouse("magazyn", "WDupie");
        warehouseRepository.save(warehouse);

        FinancialDonationCreateDTO createDTO = new FinancialDonationCreateDTO(
                financialNeed.getId(),
                200.0,
                FinancialDonation.Currency.PLN.toString()
        );

        FinancialDonation money = financialDonationService.createDonation(createDTO);
        FinancialDonation foundMoney = financialDonationRepository.findById(money.getId()).orElse(null);
        Assertions.assertNotNull(foundMoney);
    }



}
