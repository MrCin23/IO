package pl.lodz.p.ias.io.wolontariusze.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;
import pl.lodz.p.ias.io.wolontariusze.dto.UpdateVolunteerDTO;
import pl.lodz.p.ias.io.wolontariusze.services.VolunteerService;

import java.util.List;

@PreAuthorize("hasAnyRole('WOLONTARIUSZ')")
@RestController
@RequestMapping("/api/volunteers")
public class VolunteerController {
    private final AuthenticationService authenticationService;
    private final VolunteerService volunteerService;

    public VolunteerController(AuthenticationService authenticationService, VolunteerService volunteerService) {
        this.authenticationService = authenticationService;
        this.volunteerService = volunteerService;
    }

    /*@PostMapping
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
    }*/
    @PreAuthorize("hasAnyRole('WOLONTARIUSZ')")
    @GetMapping
    ResponseEntity<List<Account>> getVolunteers() {
        return new ResponseEntity<>(volunteerService.getAllVolunteers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Account> getVolunteer(@PathVariable("id") Long id) {
        return new ResponseEntity<>(volunteerService.getVolunteerById(id), HttpStatus.OK);
    }
//
//    @PutMapping("/{id}")
//    ResponseEntity<Account> updateVolunteer(@PathVariable("id") Long id, @RequestBody UpdateVolunteerDTO updateVolunteerDTO) {
//        return new ResponseEntity<>(volunteerService.updateVolunteer(id, updateVolunteerDTO.getFirstName(), updateVolunteerDTO.getLastName()), HttpStatus.OK);
//    }

//    @DeleteMapping("/{id}")
//    ResponseEntity<String> deleteVolunteer(@RequestParam Long id) {
//        volunteerService.deleteVolunteer(id);
//        return new ResponseEntity<>("dupa", HttpStatus.OK);
//    }

}
