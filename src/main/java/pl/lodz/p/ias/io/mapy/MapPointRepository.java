package pl.lodz.p.ias.io.mapy;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MapPointRepository extends JpaRepository<MapPoint, Long> {
}
