package pl.lodz.p.ias.io.wolontariusze.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Role;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Users;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.RoleRepository;
import pl.lodz.p.ias.io.wolontariusze.model.VolunteerGroup;
import pl.lodz.p.ias.io.wolontariusze.model.VolunteerGroupRepository;
import pl.lodz.p.ias.io.wolontariusze.model.VolunteerRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class VolunteerGroupService {
    private final VolunteerGroupRepository volunteerGroupRepository;
    private final VolunteerRepository volunteerRepository;
    private final RoleRepository roleRepository;

    public VolunteerGroupService(VolunteerGroupRepository volunteerGroupRepository, VolunteerRepository volunteerRepository, RoleRepository roleRepository) {
        this.volunteerGroupRepository = volunteerGroupRepository;
        this.volunteerRepository = volunteerRepository;
        this.roleRepository = roleRepository;
    }

    public VolunteerGroup createGroup(String name) {
        if (volunteerGroupRepository.existsByName(name)) {
            throw new IllegalArgumentException("Group name already exists");
        }
        VolunteerGroup group = new VolunteerGroup(name, new HashSet<>());
        return volunteerGroupRepository.save(group);
    }

    @Transactional
    public VolunteerGroup addMembersToGroup(Long groupId, Set<Long> userIds) {
        // Fetch the group by id
        VolunteerGroup group = volunteerGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        // Fetch all users by userIds
        Role role = roleRepository.findByRoleName("WOLONTARIUSZ");
        Set<Users> users = volunteerRepository.findAllByUserIdInAndRole(userIds, role);

        if (users.size() != userIds.size()) {
            throw new IllegalArgumentException("Some users not found");
        }

        // Add the users to the group
        group.addMembers(users);

        // Save the group with updated members
        return volunteerGroupRepository.save(group);
    }

    public List<VolunteerGroup> getAllGroups() {
        return volunteerGroupRepository.findAll();
    }
}

