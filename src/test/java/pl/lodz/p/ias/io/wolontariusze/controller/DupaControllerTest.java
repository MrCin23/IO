package pl.lodz.p.ias.io.wolontariusze.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.lodz.p.ias.io.komunikacja.dto.MessageDTO;

import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class DupaControllerTest {
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api/dupa";
    }

    @Test
    public void sendMessagePositiveTest() {
        System.out.print( RestAssured.get("/Hello World").getBody().asString());
    }

}