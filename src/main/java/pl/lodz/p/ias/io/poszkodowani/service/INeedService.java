package pl.lodz.p.ias.io.poszkodowani.service;

import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.poszkodowani.model.ManualNeed;
import pl.lodz.p.ias.io.poszkodowani.model.Need;

import java.util.List;
import java.util.Optional;

public interface INeedService<T extends Need> {
    T createNeed(T need);

    List<T> getAllNeeds();

    List<T> getNeedsByUserId(Long userId);

    Optional<T> changeStatus(Long id, Need.Status newStatus);
}
