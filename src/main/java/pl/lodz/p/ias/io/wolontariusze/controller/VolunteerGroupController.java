package pl.lodz.p.ias.io.wolontariusze.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.wolontariusze.dto.AddVolunteersDTO;
import pl.lodz.p.ias.io.wolontariusze.dto.CreateVolunteerGroupDTO;
import pl.lodz.p.ias.io.wolontariusze.model.VolunteerGroup;
import pl.lodz.p.ias.io.wolontariusze.services.VolunteerGroupService;

import java.util.List;

@RestController
@RequestMapping("/api/volunteerGroups")
public class VolunteerGroupController {

    private final VolunteerGroupService volunteerGroupService;

    public VolunteerGroupController(VolunteerGroupService volunteerGroupService) {
        this.volunteerGroupService = volunteerGroupService;
    }

    @PostMapping
    public ResponseEntity<VolunteerGroup> createGroup(@RequestBody @Valid CreateVolunteerGroupDTO createVolunteerGroupDTO) {
        try {
            VolunteerGroup newGroup = volunteerGroupService.createGroup(createVolunteerGroupDTO.getGroupName());
            return ResponseEntity.status(HttpStatus.CREATED).body(newGroup);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/{groupId}/addMembers")
    public ResponseEntity<VolunteerGroup> addMembersToGroup(@PathVariable Long groupId, @RequestBody AddVolunteersDTO addVolunteersDTO) {
        try {
            VolunteerGroup updatedGroup = volunteerGroupService.addMembersToGroup(groupId, addVolunteersDTO.getMembers());
            return ResponseEntity.ok(updatedGroup);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/{groupId}/removeMembers")
    public ResponseEntity<VolunteerGroup> removeMembersFromGroup(@PathVariable Long groupId, @RequestBody AddVolunteersDTO addVolunteersDTO) {
        try {
            VolunteerGroup updatedGroup = volunteerGroupService.removeMembersFromGroup(groupId, addVolunteersDTO.getMembers());
            return ResponseEntity.ok(updatedGroup);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<VolunteerGroup>> getAllGroups() {
        return ResponseEntity.ok(volunteerGroupService.getAllGroups());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolunteerGroup> getGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(volunteerGroupService.getGroupById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long id) {
        volunteerGroupService.deleteGroupById(id);
        return ResponseEntity.ok("dupa");
    }
}