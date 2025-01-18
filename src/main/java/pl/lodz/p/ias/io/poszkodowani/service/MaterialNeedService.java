package pl.lodz.p.ias.io.poszkodowani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;
import pl.lodz.p.ias.io.poszkodowani.model.Need;
import pl.lodz.p.ias.io.poszkodowani.repository.MaterialNeedRepository;
import pl.lodz.p.ias.io.powiadomienia.Interfaces.INotificationService;
import pl.lodz.p.ias.io.powiadomienia.notification.NotificationType;

import java.util.*;

@Service
public class MaterialNeedService {

    private final MaterialNeedRepository materialNeedRepository;

    private final INotificationService notificationService;

    @Autowired
    public MaterialNeedService(MaterialNeedRepository materialNeedRepository, INotificationService notificationService) {
        this.materialNeedRepository = materialNeedRepository;
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

    public MaterialNeed createMaterialNeed(MaterialNeed materialNeed) {
        materialNeed.setCreationDate(new Date());
        materialNeed.setPriority(1);
        materialNeed.setStatus(Need.Status.PENDING);

        MaterialNeed savedMaterialNeed = materialNeedRepository.save(materialNeed);

        notificationService.notify("New material need with id " + savedMaterialNeed.getId() + " has been created successfully", NotificationType.INFORMATION, savedMaterialNeed.getUser());

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

            if (canTransition(currentStatus, newStatus)) {
                materialNeed.setStatus(newStatus);
                materialNeedRepository.save(materialNeed);
                notificationService.notify("Status of material need with id " + materialNeed.getId() + " has been changed to " + newStatus, NotificationType.INFORMATION, materialNeed.getUser());
            } else {
                throw new IllegalStateException(
                    String.format("Cannot transition from %s to %s", currentStatus, newStatus)
                );
            }
        });

        return optionalMaterialNeed;
    }

    public void completeMaterialNeed(Long id) {
        Optional<MaterialNeed> optionalMaterialNeed = materialNeedRepository.findById(id);

        if (optionalMaterialNeed.isPresent()) {
            MaterialNeed materialNeed = optionalMaterialNeed.get();
            materialNeed.setStatus(Need.Status.COMPLETED);
            materialNeedRepository.save(materialNeed);
            notificationService.notify("Material need with id " + materialNeed.getId() + " has been completed", NotificationType.INFORMATION, materialNeed.getUser());
        } else {
            throw new NoSuchElementException("MaterialNeed with id " + id + " not found");
        }
    }
}