package pl.lodz.p.ias.io.mapy.model;

import com.google.maps.model.LatLng;
import jakarta.validation.constraints.NotNull;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;

public interface IMapPoint {
    double checkDistance(MapPoint volunteer);

    long getPointID();

    LatLng getCoordinates();

    void setCoordinates(@NotNull LatLng coordinates);

    String getTitle();

    void setTitle(@NotNull String title);

    String getDescription();

    void setDescription(String description);

    boolean isActive();

    void setActive(@NotNull boolean active);

    PointType getType();

    void setType(@NotNull PointType type);

    Account getPointOwner();

    void setPointOwner(Account pointOwner);
}
