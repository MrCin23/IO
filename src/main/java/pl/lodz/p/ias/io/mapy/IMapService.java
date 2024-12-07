package pl.lodz.p.ias.io.mapy;

import java.util.List;

public interface IMapService {
    MapPoint addPoint(MapPoint point);
    MapPoint getPoint(double x, double y);
    MapPoint getPoint(long id);
    List<MapPoint> getPoints();
    void removePoint(long id);
    void changeStatus(long id, boolean status);
}
