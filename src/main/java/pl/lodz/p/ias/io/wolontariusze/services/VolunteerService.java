package pl.lodz.p.ias.io.wolontariusze.services;

import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Users;
import pl.lodz.p.ias.io.wolontariusze.model.VolunteerRepository;

import java.util.List;

@Service
public class VolunteerService {
    VolunteerRepository volunteerRepository;
    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }


    public void addVolunteer(Users user) {
        volunteerRepository.save(user);
    }

    public List<Users> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    public Users getVolunteerById(Long id) {
        return volunteerRepository.findById(id).orElse(null);
    }
    public void deleteVolunteer(Long id) {
        volunteerRepository.deleteById(id);
    }
}
