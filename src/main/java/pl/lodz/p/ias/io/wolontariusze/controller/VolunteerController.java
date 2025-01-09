package pl.lodz.p.ias.io.wolontariusze.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;
import pl.lodz.p.ias.io.wolontariusze.constants.VolunteerConstants;
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

    public VolunteerController(AuthenticationService authenticationService, VolunteerService volunteerService) {
        this.authenticationService = authenticationService;
        this.volunteerService = volunteerService;
    }

    @PostMapping
    ResponseEntity<Map<String, String>> createVolunteer(@RequestBody @Valid CreateVolunteerDTO createVolunteerDTO) {
        Account volunteer = authenticationService.register(
                createVolunteerDTO.getUsername(),
                createVolunteerDTO.getPassword(),
                createVolunteerDTO.getFirstName(),
                createVolunteerDTO.getLastName(),
                VolunteerConstants.ROLE);
        Map<String, String> response = new HashMap<>();
        response.put("id", volunteer.getId().toString());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<Account>> getVolunteers() {
        return new ResponseEntity<>(volunteerService.getAllVolunteers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Account> getVolunteer(@PathVariable("id") Long id) {
        return new ResponseEntity<>(volunteerService.getVolunteerById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteVolunteer(@RequestParam Long id) {
        volunteerService.deleteVolunteer(id);
        return new ResponseEntity<>("dupa", HttpStatus.OK);
    }



}
