package pl.lodz.p.ias.io.darczyncy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import pl.lodz.p.ias.io.darczyncy.dto.create.ItemDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;
import pl.lodz.p.ias.io.darczyncy.repositories.ItemDonationRepository;
import pl.lodz.p.ias.io.darczyncy.services.implementations.ItemDonationService;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;
import pl.lodz.p.ias.io.poszkodowani.model.Need;
import pl.lodz.p.ias.io.poszkodowani.repository.MaterialNeedRepository;
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
public class ItemDonationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ItemDonationService itemDonationService;

    @Autowired
    private ItemDonationRepository itemDonationRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    MaterialNeedRepository materialNeedRepository;

    @BeforeEach
    public void setUp() {
        itemDonationRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void createItemDonation() {

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
        MaterialNeed materialNeed = MaterialNeed.builder()
                .itemCategory(MaterialNeed.ItemCategory.HOUSEHOLD)
                .mapPointId(2L)
                .expirationDate(Date.from(Instant.now().plus(2, ChronoUnit.DAYS)))
                .status(Need.Status.PENDING)
                .priority(2)
                .creationDate(Date.from(Instant.now().minus(1, ChronoUnit.DAYS)))
                .description("czajnik")
                .user(newUser)
        .build();
        materialNeedRepository.save(materialNeed);

        Warehouse warehouse = new Warehouse("magazyn", "WDupie");
        warehouseRepository.save(warehouse);



        ItemDonationCreateDTO createDTO = new ItemDonationCreateDTO(
                "czajnik",
                materialNeed.getId(),
                ItemDonation.ItemCategory.HOUSEHOLD.toString(),
                "bardzo dobry teapot",
                418
        );

        ItemDonation item = itemDonationService.createItemDonation(createDTO);
        ItemDonation foundDonation = itemDonationRepository.findById(item.getId()).orElse(null);
        Assertions.assertNotNull(foundDonation);

    }

}
