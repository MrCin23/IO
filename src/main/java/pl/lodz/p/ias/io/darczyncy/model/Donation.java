package pl.lodz.p.ias.io.darczyncy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.zasoby.model.Resource;
import pl.lodz.p.ias.io.zasoby.utils.ResourceStatus;

import java.time.LocalDate;

/**
 * Klasa abstrakcyjna reprezentująca darowiznę.
 * Rozszerza klasę {@link pl.lodz.p.ias.io.zasoby.model.Resource},
 * co oznacza, że darowizna jest specyficznym rodzajem zasobu.
 *
 * <p>Klasa wykorzystuje adnotacje z biblioteki JPA (Java Persistence API)
 * oraz Lombok w celu uproszczenia zarządzania właściwościami i
 * operacjami ORM.</p>
 *
 * <p>Każda darowizna jest przypisana do konkretnego darczyńcy
 * reprezentowanego przez klasę {@link pl.lodz.p.ias.io.uwierzytelnianie.model.Account}.</p>
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class Donation extends Resource {

    /**
     * Darczyńca, który przekazał darowiznę.
     * Powiązanie z tabelą użytkowników przy użyciu relacji wiele-do-jednego.
     */
    @ManyToOne
    @JoinColumn(
            name = "donor_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "donation_user_id_fk")
    )
    private Account donor;

    /**
     * Data przekazania darowizny.
     */
    LocalDate donationDate;

    /**
     * Konstruktor klasy Donation.
     *
     * @param donor           Darczyńca przekazujący darowiznę.
     * @param resourceName    Nazwa zasobu będącego przedmiotem darowizny.
     * @param resourceType    Typ zasobu.
     * @param donationDate    Data przekazania darowizny.
     * @param resourceQuantity Ilość zasobu przekazanego w ramach darowizny.
     * @param warehouseId     Identyfikator magazynu, w którym zasób został zdeponowany.
     */
    public Donation(Account donor, String resourceName, String resourceType, LocalDate donationDate,
                    int resourceQuantity, Long warehouseId, ResourceStatus resourceStatus) {
        super(resourceName, resourceType, resourceQuantity, warehouseId, resourceStatus);
        this.donor = donor;
        this.donationDate = donationDate;
    }
}

