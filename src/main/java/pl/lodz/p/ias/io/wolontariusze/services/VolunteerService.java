package pl.lodz.p.ias.io.wolontariusze.services;

import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.wolontariusze.model.VolunteerRepository;

import java.util.List;

@Service
public class VolunteerService {
    VolunteerRepository volunteerRepository;
    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }


    public List<Account> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    public Account getVolunteerById(Long id) {
        return volunteerRepository.findById(id).orElse(null);
    }
    public void deleteVolunteer(Long id) {
        volunteerRepository.deleteById(id);
    }
}
