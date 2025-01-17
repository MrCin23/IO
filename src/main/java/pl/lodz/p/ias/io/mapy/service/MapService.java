package pl.lodz.p.ias.io.mapy.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.type.MapType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ias.io.mapy.model.MapPoint;
import pl.lodz.p.ias.io.mapy.model.PointType;
import pl.lodz.p.ias.io.mapy.repository.MapPointRepository;
import pl.lodz.p.ias.io.powiadomienia.Interfaces.INotificationService;
import pl.lodz.p.ias.io.powiadomienia.notification.NotificationType;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class MapService implements IMapService {

    @Autowired
    INotificationService notificationService;

    private MapPointRepository mapPointRepository;

    @Override
    public MapPoint addPoint(MapPoint point) {
        mapPointRepository.save(point);
        List<MapPoint> volunteers = getPointsByType(PointType.VOLUNTEER);
        for(MapPoint volunteer : volunteers) {
            double dist = point.checkDistance(volunteer);
            if(dist < 10) {
                notificationService.notify("New victim within " + dist + "km needs your help! Please check: " +
                        point.getCoordinates().lat + " " + point.getCoordinates().lng, NotificationType.INFORMATION);
            }
        }
        return point;
    }

    @Override
    public MapPoint getPoint(long id) {
        return mapPointRepository.findById(id).get();
    }

    @Override
    public List<MapPoint> getPoints() {
        return mapPointRepository.findAll();
    }

    @Override
    public List<MapPoint> getPointsByType(PointType pointType) {
        return mapPointRepository.findByType(pointType);
    }

    @Override
    public void removePoint(long id) {
        Optional<MapPoint> point = mapPointRepository.findById(id);
        if(point.isPresent()) {
            MapPoint point1 = point.get();
            if(point1.getType() == PointType.VICTIM) {
                List<MapPoint> volunteers = getPointsByType(PointType.VOLUNTEER);
                for(MapPoint volunteer : volunteers) {
                    double dist = point1.checkDistance(volunteer);
                    if(dist < 10) {
                        notificationService.notify("Victim at: " +
                                point1.getCoordinates().lat + " " + point1.getCoordinates().lng + " no longer needs help!", NotificationType.INFORMATION);
                    }
                }
            }
        }
        mapPointRepository.delete(mapPointRepository.findById(id).get());
    }

    @Override
    public void changeStatus(long id, boolean status) {
        Optional<MapPoint> point = mapPointRepository.findById(id);
        if(point.isPresent()) {
            MapPoint point1 = point.get();
            if(point1.getType() == PointType.VICTIM) {
                List<MapPoint> volunteers = getPointsByType(PointType.VOLUNTEER);
                for(MapPoint volunteer : volunteers) {
                    double dist = point1.checkDistance(volunteer);
                    if(dist < 10 && status) {
                        notificationService.notify("New victim within " + dist + "km needs your help! Please check: " +
                                point1.getCoordinates().lat + " " + point1.getCoordinates().lng, NotificationType.INFORMATION);
                    } else if (dist < 10) {
                        notificationService.notify("Victim at: " +
                                point1.getCoordinates().lat + " " + point1.getCoordinates().lng + " no longer needs help!", NotificationType.INFORMATION);
                    }
                }
            }
        }
        mapPointRepository.updateActiveByPointID(id, status);
    }

    @Override
    public List<MapPoint> findByActive(boolean active) {
        return mapPointRepository.findByActive(active);
    }
}
