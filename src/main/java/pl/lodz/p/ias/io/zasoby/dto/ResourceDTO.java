package pl.lodz.p.ias.io.zasoby.dto;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.ias.io.zasoby.utils.ResourceStatus;



@Getter
@Setter
public class ResourceDTO {
    private Long resourceId;
    private String resourceName;
    private String resourceType;
    private int resourceQuantity;
    private ResourceStatus resourceStatus;
    private Long warehouseId;

    public ResourceDTO(Long resourceId, String resourceName, String resourceType,
                       int resourceQuantity, ResourceStatus resourceStatus,
                       Long warehouseId) {
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.resourceQuantity = resourceQuantity;
        this.resourceStatus = resourceStatus;
        this.warehouseId = warehouseId;
    }
}