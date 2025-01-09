package pl.lodz.p.ias.io.wolontariusze.controller;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pl.lodz.p.ias.io.wolontariusze.dto.AddVolunteersDTO;
import pl.lodz.p.ias.io.wolontariusze.dto.CreateVolunteerDTO;
import pl.lodz.p.ias.io.wolontariusze.dto.CreateVolunteerGroupDTO;

import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

class VolunteerGroupControllerTest {
    String BASE_GROUPURI = "http://localhost:8080/api/volunteerGroups";
    String BASE_VOLUNTEERURI = "http://localhost:8080/api/volunteers";
    String ID = "{groupId}";
    String ADD_MEMBERS = "/addMembers";
    String REMOVE_MEMBERS = "/removeMembers";


    @Test
    void createGroup() {
        String name = Long.toString(System.currentTimeMillis());
        String password = "dupa";
        String firstName = "Mike";
        String lastName = "Oxlong";
        CreateVolunteerDTO createVolunteerDTO = CreateVolunteerDTO.dupa(name, password, firstName, lastName);

        Response createVolunteer1 = postVolunteer(createVolunteerDTO);
        System.out.println(createVolunteer1.getBody().asString());
        Long volunteerId1 = createVolunteer1.getBody().jsonPath().getLong("id");

        name = Long.toString(System.currentTimeMillis());
        password = "dupa";
        firstName = "Hugh";
        lastName = "Janus";
        CreateVolunteerDTO createVolunteerDTO2 = CreateVolunteerDTO.dupa(name, password, firstName, lastName);
        Response createVolunteer2 = postVolunteer(createVolunteerDTO2);
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
                .statusCode(200)
                .extract().response();
        System.out.println(addMembersResponse.getBody().asString());
        int groupSize = addMembersResponse.getBody().jsonPath().getList("members").size();
        assertEquals(groupSize, members.size());
    }

    @Test
    void removeVolunteer() {
        String name = Long.toString(System.currentTimeMillis());
        String password = "dupa";
        String firstName = "Mike";
        String lastName = "Oxlong";

        Long volunteerId1 = createVolunteer(name, password, firstName, lastName);

        name = Long.toString(System.currentTimeMillis());
        password = "dupa";
        firstName = "Hugh";
        lastName = "Janus";

        Long volunteerId2 = createVolunteer(name, password, firstName, lastName);

        name = Long.toString(System.currentTimeMillis());
        password = "dupa";
        firstName = "Joe";
        lastName = "Biden";

        Long volunteerId3 = createVolunteer(name, password, firstName, lastName);

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
        members.add(volunteerId3);
        AddVolunteersDTO addVolunteersDTO = new AddVolunteersDTO(members);

        Response addMembersResponse = given()
                .baseUri(BASE_GROUPURI)
                .contentType(ContentType.JSON)
                .body(addVolunteersDTO)
                .when()
                .post(ID + ADD_MEMBERS, groupId)
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println(addMembersResponse.getBody().asString());
        int groupSize =  addMembersResponse.getBody().jsonPath().getList("members").size();
        assertEquals(groupSize, members.size());

        members.remove(volunteerId2);
        AddVolunteersDTO removeVolunteersDTO = new AddVolunteersDTO(members);

        Response removeMembersResponse = given()
                .baseUri(BASE_GROUPURI)
                .contentType(ContentType.JSON)
                .body(removeVolunteersDTO)
                .when()
                .post(ID + REMOVE_MEMBERS, groupId)
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println(addMembersResponse.getBody().asString());
        Set<Long> modifiedGroupSize =  new HashSet<>(removeMembersResponse.getBody().jsonPath().getList("members"));
        assertEquals(groupSize - members.size(), modifiedGroupSize.size());
    }

    private Long createVolunteer(String userName, String password, String firstName, String lastName) {
        CreateVolunteerDTO createVolunteerDTO2 = CreateVolunteerDTO.dupa(userName, password, firstName, lastName);
        Response createVolunteer = postVolunteer(createVolunteerDTO2);
        System.out.println(createVolunteer.getBody().asString());
        return  createVolunteer.getBody().jsonPath().getLong("id");
    }

    private Response postVolunteer(CreateVolunteerDTO createVolunteerDTO) {
        return given()
                .baseUri(BASE_VOLUNTEERURI)
                .contentType(ContentType.JSON)
                .body(createVolunteerDTO)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract().response();
    }
}