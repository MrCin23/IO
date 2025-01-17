package pl.lodz.p.ias.io.mapy.model;

import com.google.maps.model.LatLng;
import jakarta.persistence.*;
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
public class MapPoint implements IMapPoint {
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
    @Enumerated(EnumType.STRING)
    private PointType type;

    public MapPoint(LatLng coordinates, String title, String description, PointType type) {
        this.coordinates = coordinates;
        this.title = title;
        this.description = description;
        this.active = true;
        this.type = type;
    }

    public double checkDistance(MapPoint volunteer) {
        final int EARTH_RADIUS_KM = 6371;
        double lat1 = Math.toRadians(volunteer.getCoordinates().lat);
        double lon1 = Math.toRadians(volunteer.getCoordinates().lng);
        double lat2 = Math.toRadians(this.getCoordinates().lat);
        double lon2 = Math.toRadians(this.getCoordinates().lng);
        double diffLat = lat2 - lat1;
        double diffLon = lon2 - lon1;
        double a = Math.sin(diffLat / 2) * Math.sin(diffLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(diffLon / 2) * Math.sin(diffLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }
}
