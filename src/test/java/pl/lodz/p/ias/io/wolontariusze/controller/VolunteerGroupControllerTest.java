package pl.lodz.p.ias.io.wolontariusze.controller;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pl.lodz.p.ias.io.wolontariusze.dto.AddVolunteersDTO;
import pl.lodz.p.ias.io.wolontariusze.dto.CreateVolunteerDTO;
import pl.lodz.p.ias.io.wolontariusze.dto.CreateVolunteerGroupDTO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

class VolunteerGroupControllerTest {
    String BASE_GROUPURI = "http://localhost:8080/api/volunteerGroups";
    String BASE_VOLUNTEERURI = "http://localhost:8080/api/volunteers";
    String ID = "{groupId}";
    String ADD_MEMBERS = "/addMembers";


    @Test
    void createGroup() {
        String name = Long.toString(System.currentTimeMillis());
        String password = "dupa";
        String firstName = "Mike";
        String lastName = "Oxlong";
        CreateVolunteerDTO createVolunteerDTO = CreateVolunteerDTO.dupa(name, password, firstName, lastName);

        Response createVolunteer1 = createVolunteer(createVolunteerDTO);
        System.out.println(createVolunteer1.getBody().asString());
        Long volunteerId1 = createVolunteer1.getBody().jsonPath().getLong("id");

        name = Long.toString(System.currentTimeMillis());
        password = "dupa";
        firstName = "Hugh";
        lastName = "Janus";
        CreateVolunteerDTO createVolunteerDTO2 = CreateVolunteerDTO.dupa(name, password, firstName, lastName);
        Response createVolunteer2 = createVolunteer(createVolunteerDTO2);
        Long volunteerId2 = createVolunteer2.getBody().jsonPath().getLong("id");


        System.out.println(createVolunteer2.getBody().asString());

        CreateVolunteerGroupDTO createVolunteerGroupDTO = new CreateVolunteerGroupDTO(Long.toString(System.currentTimeMillis()));
        Response createVolunteerGroup = given()
                .baseUri(BASE_GROUPURI)
                .contentType(ContentType.JSON)
                .body(createVolunteerGroupDTO)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract().response();

        System.out.println(createVolunteerGroup.getBody().asString());
        String groupId = createVolunteerGroup.getBody().jsonPath().getString("id");

        Set<Long> members = new HashSet<>();

        members.add(volunteerId1);
        members.add(volunteerId2);
        AddVolunteersDTO addVolunteersDTO = new AddVolunteersDTO(members);

        Response addMembersResponse = given()
                .baseUri(BASE_GROUPURI)
                .contentType(ContentType.JSON)
                .body(addVolunteersDTO)
                .when()
                .post(ID + ADD_MEMBERS, groupId)
                .then()
//                .statusCode(201)
                .extract().response();
        System.out.println(addMembersResponse.getBody().asString());
    }

    private Response createVolunteer(CreateVolunteerDTO createVolunteerDTO) {
        return given()
                .baseUri(BASE_VOLUNTEERURI)
                .contentType(ContentType.JSON)
                .body(createVolunteerDTO)
                .when()
                .post()
                .then()

                .extract().response();
    }
}