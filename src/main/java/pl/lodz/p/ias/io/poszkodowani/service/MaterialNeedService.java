package pl.lodz.p.ias.io.poszkodowani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;
import pl.lodz.p.ias.io.poszkodowani.model.Need;
import pl.lodz.p.ias.io.poszkodowani.repository.MaterialNeedRepository;

import java.util.*;

@Service
public class MaterialNeedService {

    private final MaterialNeedRepository materialNeedRepository;

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
    public MaterialNeedService(MaterialNeedRepository materialNeedRepository) {
        this.materialNeedRepository = materialNeedRepository;
    }

    public MaterialNeed createMaterialNeed(MaterialNeed materialNeed) {
        // TODO: logika odpowiedzialana za sprawdzenie czy użytkownik oraz punkt na mapie istnieje
        materialNeed.setCreationDate(new Date());
        materialNeed.setPriority(1);
        materialNeed.setStatus(Need.Status.PENDING);

        return materialNeedRepository.save(materialNeed);
    }

    public Optional<MaterialNeed> getMaterialNeedById(Long id) {
        return materialNeedRepository.findById(id);
    }

    public List<MaterialNeed> getAllMaterialNeeds() {
        return materialNeedRepository.findAll();
    }

    public List<MaterialNeed> getMaterialNeedsByUserId(Long userId) {
        return materialNeedRepository.findByUserId(userId);
    }

    public Optional<MaterialNeed> changeStatus(Long id, Need.Status newStatus) {
        Optional<MaterialNeed> optionalMaterialNeed = materialNeedRepository.findById(id);

        optionalMaterialNeed.ifPresent(materialNeed -> {
            Need.Status currentStatus = materialNeed.getStatus();

            // Check if the transition is allowed
            if (canTransition(currentStatus, newStatus)) {
                materialNeed.setStatus(newStatus);
                materialNeedRepository.save(materialNeed);
            } else {
                // You can throw an exception or handle the invalid transition as you see fit
                throw new IllegalStateException(
                    String.format("Cannot transition from %s to %s", currentStatus, newStatus)
                );
            }
        });

        return optionalMaterialNeed;
    }
}