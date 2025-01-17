package pl.lodz.p.ias.io.zasoby.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.ias.io.mapy.model.MapPoint;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue(generator = "Long")
    private Long id;

    @NotBlank(message = "Warehouse name cannot be blank")
    private String warehouseName;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    @OneToOne
    private MapPoint mapPoint;

    public Warehouse(String warehouseName, String location, MapPoint mapPoint) {
        this.warehouseName = warehouseName;
        this.location = location;
        this.mapPoint = mapPoint;
    }
}
