package pl.lodz.p.ias.io.darczyncy;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;
import pl.lodz.p.ias.io.darczyncy.providers.CertificateProvider;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Role;
import pl.lodz.p.ias.io.zasoby.model.Warehouse;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@SpringBootTest
public class PdfBuilderTest {

    CertificateProvider certificateProvider = new CertificateProvider();

    Role role;
    Account account;
    FinancialNeed financialNeed;
    Warehouse warehouse;
    MaterialNeed materialNeed;
    FinancialDonation financialDonation;
    ItemDonation itemDonation;

    @BeforeEach
    public void setUp() {
        role = new Role("DARCZYNCA");
        account =new Account(
                "User",
                "Password",
                role,
                "Jan",
                "Nowak"
        );

        financialNeed = FinancialNeed.builder()
                .collectionGoal(200)
                .collectionStatus(2)
                .description("pomoc dla powodzian z Poznania")
                .mapPointId(2L)
                .expirationDate(Date.from(Instant.now().plus(2, ChronoUnit.DAYS)))
                .status("Test")
                .priority(2)
                .creationDate(Date.from(Instant.now().minus(1, ChronoUnit.DAYS)))
                .userId(account.getId())
                .build();

        warehouse = new Warehouse("magazyn", "WDupie");


        materialNeed = MaterialNeed.builder()
                .product("czajnik")
                .amount(1)
                .mapPointId(2L)
                .expirationDate(Date.from(Instant.now().plus(2, ChronoUnit.DAYS)))
                .status("Test")
                .priority(2)
                .creationDate(Date.from(Instant.now().minus(1, ChronoUnit.DAYS)))
                .description("zasób materialny")
                .userId(account.getId())
                .build();

        itemDonation = new ItemDonation(
                account,
                materialNeed,
                "czajnik",
                418,
                warehouse.getId(),
                ItemDonation.ItemCategory.HOUSEHOLD,
                "bardzo dobry teapot",
                LocalDate.now()
        );

        financialDonation = new FinancialDonation(
                account,
                financialNeed,
                warehouse.getId(),
                100,
                FinancialDonation.Currency.PLN,
                LocalDate.now()
        );
    }

    @Test
    public void pdfBuilderTestItem() {
        certificateProvider.generateItemCertificate(account, itemDonation);
    }

    @Test
    public void pdfBuilderTestFinancial() {

        certificateProvider.generateFinancialCertificate(account, financialDonation);
    }


}
