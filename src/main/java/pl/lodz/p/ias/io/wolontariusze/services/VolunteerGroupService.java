package pl.lodz.p.ias.io.wolontariusze.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Role;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.RoleRepository;
import pl.lodz.p.ias.io.wolontariusze.constants.VolunteerConstants;
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

    public List<VolunteerGroup> getAllGroups() {
        return volunteerGroupRepository.findAll();
    }

    public VolunteerGroup getGroupById(Long id) {
        return volunteerGroupRepository.findById(id).orElse(null);
    }

    public VolunteerGroup createGroup(String name) {
        if (volunteerGroupRepository.existsByName(name)) {
            throw new IllegalArgumentException("Group name already exists");
        }
        VolunteerGroup group = new VolunteerGroup(name, new HashSet<>());
        return volunteerGroupRepository.save(group);
    }

    private Set<Account> getVolunteerAccounts(Set<Long> userIds) {
        Role role = roleRepository.findByRoleName(VolunteerConstants.ROLE);
        Set<Account> users = volunteerRepository.findAllByIdInAndRole(userIds, role);
        if (users.size() != userIds.size()) {
            throw new IllegalArgumentException("Some users not found");
        }
        return users;
    }

    public List<Account> getNotMembersByGroupId(Long id) {
        Role role = roleRepository.findByRoleName(VolunteerConstants.ROLE);
        List<Account> allVolunteers = volunteerRepository.findAllByRole(role);
        VolunteerGroup group = volunteerGroupRepository.findById(id).orElse(null);
        allVolunteers.removeAll(group.getMembers());
//        Set<Account> restVolunteers = allVolunteers.removeAll(group.getMembers());
//        List<Account> allVolunteers = volunteerRepository.findAll()
//        return volunteerGroupRepository.findById(id).orElse(null);
        return allVolunteers;
    }



    @Transactional
    public VolunteerGroup addMembersToGroup(Long groupId, Set<Long> userIds) {
        VolunteerGroup group = volunteerGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        group.addMembers(getVolunteerAccounts(userIds));
        return volunteerGroupRepository.save(group);
    }

    @Transactional
    public VolunteerGroup removeMembersFromGroup(Long groupId, Set<Long> userIds) {
        VolunteerGroup group = volunteerGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        group.removeMembers(getVolunteerAccounts(userIds));
        return volunteerGroupRepository.save(group);
    }

    public void deleteGroupById(Long groupId) {
        volunteerGroupRepository.deleteById(groupId);
    }
}

