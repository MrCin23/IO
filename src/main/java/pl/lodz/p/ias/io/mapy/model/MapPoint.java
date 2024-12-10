package pl.lodz.p.ias.io.mapy.model;

import com.google.maps.model.LatLng;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MapPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pointID;
    @NotNull
    private LatLng coordinates;
    @NotNull
    private String title;
    private String description;
    @NotNull
    private boolean active;
    @NotNull
    private PointType type;

    public MapPoint(LatLng coordinates, String title, String description, PointType type) {
        this.coordinates = coordinates;
        this.title = title;
        this.description = description;
        this.active = true;
        this.type = type;
    }
}
