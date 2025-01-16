package pl.lodz.p.ias.io.mapy;

import com.google.maps.model.LatLng;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;


public class MapTest {


    @BeforeEach
    public void initCollection() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/map";
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
                .body("coordinates.lat", equalTo(2.137F))
                .body("coordinates.lng", equalTo(4.20F));
    }

    @Test
    public void testRestGet() {
        String payloadJson = """
                {
                    "coordinates": {
                        "lat": 2.137,
                        "lng": 4.20
                    },
                    "title": "Potrzebna pomoc",
                    "type": "VOLUNTEER"
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
                .get("/{id}", 1)
                .then()
                .statusCode(200)
                .body("title", equalTo("Potrzebna pomoc"))
                .body("coordinates.lat", equalTo(2.137F))
                .body("coordinates.lng", equalTo(4.20F));
    }

//    @Test
//    public void testRestDelete() {
//        String payloadJson = """
//                {
//                    "coordinates": {
//                        "lat": 2.137,
//                        "lng": 4.20
//                    },
//                    "title": "Potrzebna pomoc",
//                    "type": "VOLUNTEER"
//                }""";
//
//        RestAssured.given()
//                .contentType(ContentType.JSON)
//                .body(payloadJson)
//                .when()
//                .post()
//                .then()
//                .statusCode(201);
//
//        RestAssured.given()
//                .when()
//                .delete("/{id}", 1)
//                .then()
//                .statusCode(204);
//    }
}