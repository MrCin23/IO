package pl.lodz.p.ias.io.darczyncy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.ias.io.poszkodowani.model.Need;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Users;
import pl.lodz.p.ias.io.zasoby.model.Resource;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Donation extends Resource {
    public enum AcceptanceStatus {
        ACCEPTED, REJECTED, PENDING
    }

    @ManyToOne
    @JoinColumn(
            name = "donor_id",
            referencedColumnName = "userId",
            foreignKey = @ForeignKey(name = "donation_user_id_fk")
    )
    private Users donor;

    @ManyToOne
    @JoinColumn(
            name = "need_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "donation_need_id_fk")
    )
    private Need need;

    @Enumerated(EnumType.STRING)
    private AcceptanceStatus acceptanceStatus;

    public Donation (Users donor, Need need, String resourceName, String resourceType,
                     int resourceQuantity, long warehouseId) {
        super(resourceName, resourceType, resourceQuantity, warehouseId);
        this.donor = donor;
        this.need = need;
        this.acceptanceStatus = AcceptanceStatus.PENDING;
    }

}
