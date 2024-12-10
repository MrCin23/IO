package pl.lodz.p.ias.io.darczyncy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.ias.io.zasoby.model.Resource;

@Getter @Setter
@NoArgsConstructor
public class Donation extends Resource {
    public enum AcceptanceStatus {
        ACCEPTED, REJECTED, PENDING
    }

    public static class User {
        //todo: take class from Security module
    }

    public static class Need {
        //todo: take class from Needs module
    }

    private User donor;
    private Need need;
    private AcceptanceStatus acceptanceStatus;

    public Donation (User donor, Need need, String resourceName, String resourceType,
                     int resourceQuantity, long warehouseId) {
        super(resourceName, resourceType, resourceQuantity, warehouseId);
        this.donor = donor;
        this.need = need;
        this.acceptanceStatus = AcceptanceStatus.PENDING;
    }

}
