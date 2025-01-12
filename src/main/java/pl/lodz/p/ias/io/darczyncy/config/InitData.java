package pl.lodz.p.ias.io.darczyncy.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.lodz.p.ias.io.darczyncy.dto.create.ItemDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;
import pl.lodz.p.ias.io.darczyncy.repositories.ItemDonationRepository;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;
import pl.lodz.p.ias.io.poszkodowani.repository.FinancialNeedRepository;
import pl.lodz.p.ias.io.poszkodowani.repository.MaterialNeedRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Role;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.RoleRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.UserRepository;
import pl.lodz.p.ias.io.zasoby.model.Warehouse;
import pl.lodz.p.ias.io.zasoby.repository.WarehouseRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final WarehouseRepository warehouseRepository;
    private final FinancialNeedRepository financialNeedRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final MaterialNeedRepository materialNeedRepository;
    private final ItemDonationRepository itemDonationRepository;

    @Override
    public void run(String... args) throws Exception {
        Role role = new Role("DARCZYNCA");
        roleRepository.save(role);
        Account newUser = userRepository.save(new Account(
                "User",
                "Password",
                role,
                "Jan",
                "Nowak"
        ));

        FinancialNeed financialNeed = FinancialNeed.builder()
                .collectionGoal(200)
                .collectionStatus(2)
                .description("aa")
                .mapPointId(2L)
                .expirationDate(Date.from(Instant.now().plus(2, ChronoUnit.DAYS)))
                .status("Test")
                .priority(2)
                .creationDate(Date.from(Instant.now().minus(1, ChronoUnit.DAYS)))
                .userId(newUser.getId())
                .build();
        financialNeedRepository.save(financialNeed);
        Warehouse warehouse = new Warehouse("magazyn", "WDupie");
        warehouseRepository.save(warehouse);

        MaterialNeed materialNeed = MaterialNeed.builder()
                .product("czajnik")
                .amount(1)
                .mapPointId(2L)
                .expirationDate(Date.from(Instant.now().plus(2, ChronoUnit.DAYS)))
                .status("Test")
                .priority(2)
                .creationDate(Date.from(Instant.now().minus(1, ChronoUnit.DAYS)))
                .description("zasób materialny")
                .userId(newUser.getId())
                .build();
        materialNeedRepository.save(materialNeed);

        ItemDonation itemDonation = new ItemDonation(
                newUser,
                materialNeed,
                "czajnik",
                418,
                warehouse.getId(),
                ItemDonation.ItemCategory.HOUSEHOLD,
                "bardzo dobry teapot"
        );
        itemDonationRepository.save(itemDonation);
    }
}
