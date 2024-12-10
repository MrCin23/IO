package pl.lodz.p.ias.io.komunikacja;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.lodz.p.ias.io.komunikacja.dto.MessageDTO;
import static org.hamcrest.Matchers.equalTo;

import java.util.Date;

public class MessageTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api/messages";
    }

    @Test
    public void sendMessagePositiveTest() {
        MessageDTO messageDTO = new MessageDTO("Witam", "Bartek", "Wojtek", new Date());
        RestAssured.given()
                .body(messageDTO)
                .contentType("application/json")
                .when()
                .post()
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("content", equalTo("Wojtek"))
                .body("receiver", equalTo("Bartek"))
                .body("sender", equalTo("Witam"));

    }
}
