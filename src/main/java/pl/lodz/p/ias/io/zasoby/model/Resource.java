package pl.lodz.p.ias.io.zasoby.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.ias.io.zasoby.utils.ResourceStatus;

@Getter
@Setter
@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Resource name cannot be blank")
    private String resourceName;

    @NotBlank(message = "Resource type cannot be blank")
    private String resourceType;

    @Min(value = 1, message = "Resource quantity must be at least 1")
    private int resourceQuantity;

    @NotNull(message = "Resource status cannot be null")
    private ResourceStatus resourceStatus;

    private Long warehouseId;

    private String volunteerName;

    private String assignedTask;

    private int alertThreshold;

    public Resource(String resourceName, String resourceType, int resourceQuantity, Long warehouseId) {
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.resourceQuantity = resourceQuantity;
        this.warehouseId = warehouseId;
        this.resourceStatus = ResourceStatus.PENDING;
    }

    public Resource(String resourceName, String resourceType, int resourceQuantity, Long warehouseId, ResourceStatus resourceStatus) {
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.resourceQuantity = resourceQuantity;
        this.warehouseId = warehouseId;
        this.resourceStatus = resourceStatus;
    }
}