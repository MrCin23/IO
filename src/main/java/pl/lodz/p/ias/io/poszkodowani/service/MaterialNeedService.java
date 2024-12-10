package pl.lodz.p.ias.io.poszkodowani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;
import pl.lodz.p.ias.io.poszkodowani.repository.MaterialNeedRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialNeedService {

    private final MaterialNeedRepository materialNeedRepository;

    @Autowired
    public MaterialNeedService(MaterialNeedRepository materialNeedRepository) {
        this.materialNeedRepository = materialNeedRepository;
    }

    public MaterialNeed createMaterialNeed(MaterialNeed materialNeed) {
        return materialNeedRepository.save(materialNeed);
    }

    public Optional<MaterialNeed> getMaterialNeedById(Long id) {
        return materialNeedRepository.findById(id);
    }

    public List<MaterialNeed> getAllMaterialNeeds() {
        return materialNeedRepository.findAll();
    }
}