package pl.lodz.p.ias.io.zasoby.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Warehouse name cannot be blank")
    private String warehouseName;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    public Warehouse(String warehouseName, String location) {
        this.warehouseName = warehouseName;
        this.location = location;
    }
}