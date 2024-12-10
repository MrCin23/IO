package pl.lodz.p.ias.io.poszkodowani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.poszkodowani.model.ManualNeed;
import pl.lodz.p.ias.io.poszkodowani.repository.ManualNeedRepository;

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
        return manualNeedRepository.save(manualNeed);
    }

    public Optional<ManualNeed> getManualNeedById(Long id) {
        return manualNeedRepository.findById(id);
    }

    public List<ManualNeed> getAllManualNeeds() {
        return manualNeedRepository.findAll();
    }
}
