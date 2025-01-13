package pl.lodz.p.ias.io.darczyncy.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.lodz.p.ias.io.darczyncy.dto.create.ItemDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;
import pl.lodz.p.ias.io.darczyncy.repositories.FinancialDonationRepository;
import pl.lodz.p.ias.io.darczyncy.repositories.ItemDonationRepository;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;
import pl.lodz.p.ias.io.poszkodowani.model.Need;
import pl.lodz.p.ias.io.poszkodowani.repository.FinancialNeedRepository;
import pl.lodz.p.ias.io.poszkodowani.repository.MaterialNeedRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Role;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.RoleRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.UserRepository;
import pl.lodz.p.ias.io.zasoby.model.Warehouse;
import pl.lodz.p.ias.io.zasoby.repository.WarehouseRepository;

import java.time.Instant;
import java.time.LocalDate;
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
    private final FinancialDonationRepository financialDonationRepository;

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
                .status(Need.Status.PENDING)
                .priority(2)
                .creationDate(Date.from(Instant.now().minus(1, ChronoUnit.DAYS)))
                .user(newUser)
                .build();
        financialNeedRepository.save(financialNeed);
        Warehouse warehouse = new Warehouse("magazyn", "WDupie");
        warehouseRepository.save(warehouse);

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

        ItemDonation itemDonation = new ItemDonation(
                newUser,
                materialNeed,
                "czajnik",
                418,
                warehouse.getId(),
                ItemDonation.ItemCategory.HOUSEHOLD,
                "bardzo dobry teapot",
                LocalDate.now()
        );
        itemDonationRepository.save(itemDonation);

        FinancialDonation financialDonation = new FinancialDonation(
                newUser,
                financialNeed,
                warehouse.getId(),
                100,
                FinancialDonation.Currency.PLN,
                LocalDate.now()
        );
        financialDonationRepository.save(financialDonation);
    }
}
