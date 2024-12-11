package pl.lodz.p.ias.io.mapy;

import com.google.maps.model.LatLng;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import pl.lodz.p.ias.io.mapy.model.MapPoint;
import pl.lodz.p.ias.io.mapy.model.PointType;
import pl.lodz.p.ias.io.mapy.repository.MapPointRepository;
import pl.lodz.p.ias.io.mapy.service.MapService;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {MapService.class})
@ComponentScan(basePackages = "pl.lodz.p.ias.io.mapy.repository")
public class MapTest {

    @Autowired
    private MapService mapService;

    @Autowired
    private MapPointRepository mapPointRepository;

    @BeforeEach
    public void setUp() {
        mapPointRepository.deleteAll();
    }

    @Test
    public void testAddPoint() {
        LatLng coord = new LatLng(21,37);
        MapPoint point = new MapPoint(coord, "title", "description", PointType.VOLUNTEER);
        long id = point.getPointID();
        mapService.addPoint(point);
        Assertions.assertEquals(mapService.getPoint(id).toString(), point.toString());
    }

    @Test
    public void testRemovePoint() {
        LatLng coord = new LatLng(21,37);
        MapPoint point = new MapPoint(coord, "title", "description", PointType.VOLUNTEER);
        long id = point.getPointID();
        mapService.addPoint(point);
        Assertions.assertEquals(mapService.getPoints().size(), 1);
        mapService.removePoint(id);
        Assertions.assertEquals(mapService.getPoints().size(), 0);
    }

    @Test
    public void testRestPost(){
        String payloadJson = """
                {
                    "coordinates": {
                        "lat": 2.137,
                        "lng": 4.20
                    },
                    "title": "Potrzebna pomoc",
                    "type": "VICTIM"
                }""";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("title", equalTo("Potrzebna pomoc"))
                .body("coordinates.lat", equalTo(2.137))
                .body("coordinates.lng", equalTo(4.20));
    }

    @Test
    public void testRestGet() {
        String payloadJson = """
                {
                    "id": 111,
                    "coordinates": {
                        "lat": 2.137,
                        "lng": 4.20
                    },
                    "title": "Potrzebna pomoc",
                    "type": "VICTIM"
                }""";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .when()
                .post()
                .then()
                .statusCode(201);

        RestAssured.given()
                .when()
                .get("/{id}", 111)
                .then()
                .statusCode(200)
                .body("title", equalTo("Potrzebna pomoc"))
                .body("coordinates.lat", equalTo(2.137))
                .body("coordinates.lng", equalTo(4.20));
    }
}