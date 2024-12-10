package pl.lodz.p.ias.io.zasoby.dto;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.ias.io.zasoby.utils.ResourceStatus;

@Getter
@Setter
public class ResourceDTO {
    private String resourceName;
    private String resourceType;
    private int resourceQuantity;
    private ResourceStatus resourceStatus;
    private long warehouseId;
    private String volunteerName;
    private String assignedTask;

    public ResourceDTO(String resourceName, String resourceType,
                       int resourceQuantity, ResourceStatus resourceStatus,
                       long warehouseId, String volunteerName, String assignedTask) {
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.resourceQuantity = resourceQuantity;
        this.resourceStatus = resourceStatus;
        this.warehouseId = warehouseId;
        this.volunteerName = volunteerName;
        this.assignedTask = assignedTask;
    }
}
