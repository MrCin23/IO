package pl.lodz.p.ias.io.mapy.repository;


import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.mapy.model.MapPoint;
import pl.lodz.p.ias.io.mapy.model.PointType;

import java.util.List;


@Repository
public interface MapPointRepository extends JpaRepository<MapPoint, Long> {
    List<MapPoint> findByType(PointType type);

    void updateActiveByPointID(long pointID, boolean isActive);

    List<MapPoint> findByActive(@NotNull boolean active);
}
