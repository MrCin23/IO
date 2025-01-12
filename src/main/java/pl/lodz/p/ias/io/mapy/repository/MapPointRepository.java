package pl.lodz.p.ias.io.mapy.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.mapy.model.MapPoint;


@Repository
public interface MapPointRepository extends JpaRepository<MapPoint, Long> {
}
