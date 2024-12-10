package pl.lodz.p.ias.io.zasoby.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//TODO: zamienic location w zaleznosci od odpowiednich modulow

@Getter
@Setter
@Entity
@Table(name = "warehouses")
@NoArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String warehouseName;
    private String location;

    public Warehouse(String warehouseName, String location) {
        this.warehouseName = warehouseName;
        this.location = location;
    }
}
