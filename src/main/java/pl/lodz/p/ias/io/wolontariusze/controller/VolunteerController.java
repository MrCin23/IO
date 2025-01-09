package pl.lodz.p.ias.io.wolontariusze.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Users;
import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;
import pl.lodz.p.ias.io.wolontariusze.dto.CreateVolunteerDTO;
import pl.lodz.p.ias.io.wolontariusze.services.VolunteerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/volunteers")
public class VolunteerController {
    private final AuthenticationService authenticationService;
    private final VolunteerService volunteerService;
    private final String WOLONTARIUSZ = "WOLONTARIUSZ";

    public VolunteerController(AuthenticationService authenticationService, VolunteerService volunteerService) {
        this.authenticationService = authenticationService;
        this.volunteerService = volunteerService;
    }

    @PostMapping
    ResponseEntity<Map<String, String>> createVolunteer(@RequestBody @Valid CreateVolunteerDTO createVolunteerDTO) {
        Users volunteer = authenticationService.register(
                createVolunteerDTO.getUsername(),
                createVolunteerDTO.getPassword(),
                createVolunteerDTO.getFirstName(),
                createVolunteerDTO.getLastName(),
                WOLONTARIUSZ
        );
        Map<String, String> response = new HashMap<>();
        response.put("id", volunteer.getUserId().toString());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<Users>> getVolunteers() {
        return new ResponseEntity<>(volunteerService.getAllVolunteers(), HttpStatus.OK);
    }



}
