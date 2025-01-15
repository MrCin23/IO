package pl.lodz.p.ias.io.darczyncy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.zasoby.model.Resource;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@MappedSuperclass
public abstract class Donation extends Resource {
    public enum AcceptanceStatus {
        ACCEPTED, REJECTED, PENDING
    }

    @ManyToOne
    @JoinColumn(
            name = "donor_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "donation_user_id_fk")
    )
    private Account donor;

    @Enumerated(EnumType.STRING)
    private AcceptanceStatus acceptanceStatus;

    LocalDate donationDate;

    public Donation (Account donor, String resourceName, String resourceType, LocalDate donationDate,
                     int resourceQuantity, Long warehouseId) {
        super(resourceName, resourceType, resourceQuantity, warehouseId);
        this.donor = donor;
        this.acceptanceStatus = AcceptanceStatus.PENDING;
        this.donationDate = donationDate;
    }

}
