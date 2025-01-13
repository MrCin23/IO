package pl.lodz.p.ias.io.poszkodowani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.poszkodowani.model.ManualNeed;
import pl.lodz.p.ias.io.poszkodowani.model.Need;
import pl.lodz.p.ias.io.poszkodowani.repository.ManualNeedRepository;

import java.util.*;

@Service
public class ManualNeedService {

    private final ManualNeedRepository manualNeedRepository;
    
    private static final Map<Need.Status, Set<Need.Status>> ALLOWED_TRANSITIONS = new HashMap<>();
    
    static {
        ALLOWED_TRANSITIONS.put(Need.Status.PENDING, Set.of(Need.Status.CANCELLED, Need.Status.IN_PROGRESS));
        ALLOWED_TRANSITIONS.put(Need.Status.IN_PROGRESS, Set.of(Need.Status.COMPLETED));
        ALLOWED_TRANSITIONS.put(Need.Status.CANCELLED, Collections.emptySet());
        ALLOWED_TRANSITIONS.put(Need.Status.COMPLETED, Collections.emptySet());
    }

    private boolean canTransition(Need.Status current, Need.Status target) {
        return ALLOWED_TRANSITIONS
            .getOrDefault(current, Collections.emptySet())
            .contains(target);
    }

    @Autowired
    public ManualNeedService(ManualNeedRepository manualNeedRepository) {
        this.manualNeedRepository = manualNeedRepository;
    }

    public ManualNeed createManualNeed(ManualNeed manualNeed) {
        // TODO: logika odpowiedzialana za sprawdzenie czy użytkownik oraz punkt na mapie istnieje

        manualNeed.setCreationDate(new Date());
        manualNeed.setPriority(1);
        manualNeed.setStatus(Need.Status.PENDING);
        manualNeed.setVolunteers(0);

        return manualNeedRepository.save(manualNeed);
    }

    public Optional<ManualNeed> getManualNeedById(Long id) {
        return manualNeedRepository.findById(id);
    }

    public List<ManualNeed> getAllManualNeeds() {
        return manualNeedRepository.findAll();
    }

    public List<ManualNeed> getManualNeedsByUserId(Long userId) {
        return manualNeedRepository.findByUserId(userId);
    }

    public Optional<ManualNeed> changeStatus(Long id, Need.Status newStatus) {
        Optional<ManualNeed> optionalManualNeed = manualNeedRepository.findById(id);

        optionalManualNeed.ifPresent(manualNeed -> {
            Need.Status currentStatus = manualNeed.getStatus();
            if (canTransition(currentStatus, newStatus)) {
                manualNeed.setStatus(newStatus);
                manualNeedRepository.save(manualNeed);
            } else {
                // You can throw an exception or handle the invalid transition as you see fit
                throw new IllegalStateException(
                        String.format("Cannot transition from %s to %s", currentStatus, newStatus)
                );
            }
        });

        return optionalManualNeed;
    }
}
