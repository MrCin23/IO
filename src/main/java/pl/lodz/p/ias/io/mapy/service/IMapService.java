package pl.lodz.p.ias.io.mapy.service;

import pl.lodz.p.ias.io.mapy.model.MapPoint;
import pl.lodz.p.ias.io.mapy.model.PointType;

import java.util.List;

public interface IMapService {

    public MapPoint addPoint(MapPoint point);

//    public MapPoint getPoint(double x, double y);

    public MapPoint getPoint(long id);

    public List<MapPoint> getPoints();

    public List<MapPoint> getPointsByType(PointType pointType);

    public void removePoint(long id);

    public void changeStatus(long id, boolean status);
}
