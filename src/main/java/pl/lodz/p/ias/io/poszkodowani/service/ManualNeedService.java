package pl.lodz.p.ias.io.poszkodowani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.poszkodowani.model.ManualNeed;
import pl.lodz.p.ias.io.poszkodowani.model.Need;
import pl.lodz.p.ias.io.poszkodowani.repository.ManualNeedRepository;
import pl.lodz.p.ias.io.powiadomienia.Interfaces.INotificationService;
import pl.lodz.p.ias.io.powiadomienia.notification.NotificationType;

import java.util.*;

@Service
public class ManualNeedService implements INeedService<ManualNeed> {

    private final ManualNeedRepository manualNeedRepository;

    private final INotificationService notificationService;

    @Autowired
    public ManualNeedService(ManualNeedRepository manualNeedRepository, INotificationService notificationService) {
        this.manualNeedRepository = manualNeedRepository;
        this.notificationService = notificationService;
    }

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

    public ManualNeed createNeed(ManualNeed manualNeed) {

        manualNeed.setCreationDate(new Date());
        manualNeed.setPriority(1);
        manualNeed.setStatus(Need.Status.PENDING);
        manualNeed.setVolunteers(0);

        ManualNeed savedManualNeed = manualNeedRepository.save(manualNeed);

        notificationService.notify("New manual need with id " + savedManualNeed.getId() + " has been created successfully", NotificationType.INFORMATION, savedManualNeed.getUser());

        return savedManualNeed;
    }

    public Optional<ManualNeed> getManualNeedById(Long id) {
        return manualNeedRepository.findById(id);
    }

    public List<ManualNeed> getAllNeeds() {
        return manualNeedRepository.findAll();
    }

    public List<ManualNeed> getNeedsByUserId(Long userId) {
        return manualNeedRepository.findByUserId(userId);
    }

    public Optional<ManualNeed> changeStatus(Long id, Need.Status newStatus) {
        Optional<ManualNeed> optionalManualNeed = manualNeedRepository.findById(id);

        optionalManualNeed.ifPresent(manualNeed -> {
            Need.Status currentStatus = manualNeed.getStatus();
            if (canTransition(currentStatus, newStatus)) {
                manualNeed.setStatus(newStatus);
                manualNeedRepository.save(manualNeed);
                notificationService.notify("Status of manual need with id " + manualNeed.getId() + " has been changed to " + newStatus, NotificationType.INFORMATION, manualNeed.getUser());
            } else {
                throw new IllegalStateException(
                        String.format("Cannot transition from %s to %s", currentStatus, newStatus)
                );
            }
        });

        return optionalManualNeed;
    }
}
