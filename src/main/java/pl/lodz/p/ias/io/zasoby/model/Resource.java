package pl.lodz.p.ias.io.zasoby.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import pl.lodz.p.ias.io.zasoby.utils.ResourceStatus;

@Getter
@Setter
@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String resourceName;
    private String resourceType;
    private int resourceQuantity;
    private ResourceStatus resourceStatus;
    private Warehouse warehouse;
    private String volunteerName;
    private String assignedTask;

    public Resource(String resourceName, String resourceType, int resourceQuantity,
                    Warehouse warehouse) {
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.resourceQuantity = resourceQuantity;
        this.warehouse = warehouse;
        resourceStatus = ResourceStatus.NIEPRZYDZIELONY;
    }
}
