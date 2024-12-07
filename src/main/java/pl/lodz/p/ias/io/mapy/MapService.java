package pl.lodz.p.ias.io.mapy;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MapService implements IMapService {

    private MapPointRepository mapPointRepository;

    @Override
    public MapPoint addPoint(MapPoint point) {
        mapPointRepository.add(point);
        return point;
    }

    @Override
    public MapPoint getPoint(double x, double y) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public MapPoint getPoint(long id) {
        return mapPointRepository.findById(id);
    }

    @Override
    public List<MapPoint> getPoints() {
        return mapPointRepository.getClients();
    }

    @Override
    public void removePoint(long id) {
        mapPointRepository.remove(mapPointRepository.findById(id));
    }

    @Override
    public void changeStatus(long id, boolean status) {
        mapPointRepository.changeStatus(mapPointRepository.findById(id), status
        );
    }
}
