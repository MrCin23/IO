package pl.lodz.p.ias.io.poszkodowani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.poszkodowani.model.ManualNeed;
import pl.lodz.p.ias.io.poszkodowani.repository.ManualNeedRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ManualNeedService {

    private final ManualNeedRepository manualNeedRepository;

    @Autowired
    public ManualNeedService(ManualNeedRepository manualNeedRepository) {
        this.manualNeedRepository = manualNeedRepository;
    }

    public ManualNeed createManualNeed(ManualNeed manualNeed) {
        // TODO: logika odpowiedzialana za sprawdzenie czy użytkownik oraz punkt na mapie istnieje

        manualNeed.setCreationDate(new Date());
        manualNeed.setPriority(1);
        manualNeed.setStatus("New");
        manualNeed.setVolunteers(0);

        return manualNeedRepository.save(manualNeed);
    }

    public Optional<ManualNeed> getManualNeedById(Long id) {
        return manualNeedRepository.findById(id);
    }

    public List<ManualNeed> getAllManualNeeds() {
        return manualNeedRepository.findAll();
    }
}
