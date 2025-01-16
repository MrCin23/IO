package pl.lodz.p.ias.io.poszkodowani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.poszkodowani.model.Need;
import pl.lodz.p.ias.io.poszkodowani.repository.FinancialNeedRepository;

import java.util.*;

@Service
public class FinancialNeedService {

    private final FinancialNeedRepository financialNeedRepository;

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
    public FinancialNeedService(FinancialNeedRepository financialNeedRepository) {
        this.financialNeedRepository = financialNeedRepository;
    }

    public FinancialNeed createFinancialNeed(FinancialNeed financialNeed) {
        // TODO: logika odpowiedzialana za sprawdzenie czy użytkownik oraz punkt na mapie istnieje

        financialNeed.setCreationDate(new Date());
        financialNeed.setPriority(1);
        financialNeed.setStatus(Need.Status.PENDING);
        financialNeed.setCollectionStatus(0);

        return financialNeedRepository.save(financialNeed);
    }

    public Optional<FinancialNeed> getFinancialNeedById(Long id) {
        return financialNeedRepository.findById(id);
    }

    public List<FinancialNeed> getAllFinancialNeeds() {
        return financialNeedRepository.findAll();
    }

    public Optional<FinancialNeed> changeStatus(Long id, Need.Status newStatus) {
        Optional<FinancialNeed> optionalFinancialNeed = financialNeedRepository.findById(id);

        optionalFinancialNeed.ifPresent(financialNeed -> {
            Need.Status currentStatus = financialNeed.getStatus();

            // Check if the transition is allowed
            if (canTransition(currentStatus, newStatus)) {
                financialNeed.setStatus(newStatus);
                financialNeedRepository.save(financialNeed);
            } else {
                // You can throw an exception or handle the invalid transition as you see fit
                throw new IllegalStateException(
                    String.format("Cannot transition from %s to %s", currentStatus, newStatus)
                );
            }
        });

        return optionalFinancialNeed;
    }

    public List<FinancialNeed> getFinancialNeedByUserId(Long userId) {
        return financialNeedRepository.findByUserId(userId);
    }

    public void updateFinancialNeedCollectionStatus(Long id, double supportAmount) {
        Optional<FinancialNeed> optionalFinancialNeed = financialNeedRepository.findById(id);

        if (optionalFinancialNeed.isPresent()) {
            FinancialNeed financialNeed = optionalFinancialNeed.get();
            double newCollectionStatus = financialNeed.getCollectionStatus() + supportAmount;

            financialNeed.setCollectionStatus(newCollectionStatus);

            if (newCollectionStatus >= financialNeed.getCollectionGoal()) {
                financialNeed.setStatus(Need.Status.COMPLETED);
            } else if (newCollectionStatus > 0) {
                financialNeed.setStatus(Need.Status.IN_PROGRESS);
            }

            financialNeedRepository.save(financialNeed);
        } else {
            throw new NoSuchElementException("FinancialNeed with id " + id + " not found");
        }
    }
}