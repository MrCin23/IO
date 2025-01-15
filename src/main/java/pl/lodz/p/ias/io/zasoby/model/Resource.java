package pl.lodz.p.ias.io.zasoby.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.ias.io.zasoby.utils.ResourceStatus;

@Getter
@Setter
@Entity
@NoArgsConstructor
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

    @Min(value = 1, message = "Warehouse ID must be a positive number")
    private Long warehouseId;

    private String volunteerName;

    private String assignedTask;

    private int alertThreshold;

    public Resource(String resourceName, String resourceType, int resourceQuantity, Long warehouseId) {
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.resourceQuantity = resourceQuantity;
        this.warehouseId = warehouseId;
        this.resourceStatus = ResourceStatus.NIEPRZYDZIELONY;
    }
}