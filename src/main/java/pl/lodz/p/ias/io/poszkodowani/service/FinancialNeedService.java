package pl.lodz.p.ias.io.poszkodowani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.poszkodowani.repository.FinancialNeedRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FinancialNeedService {

    private final FinancialNeedRepository financialNeedRepository;

    @Autowired
    public FinancialNeedService(FinancialNeedRepository financialNeedRepository) {
        this.financialNeedRepository = financialNeedRepository;
    }

    public FinancialNeed createFinancialNeed(FinancialNeed financialNeed) {
        return financialNeedRepository.save(financialNeed);
    }

    public Optional<FinancialNeed> getFinancialNeedById(Long id) {
        return financialNeedRepository.findById(id);
    }

    public List<FinancialNeed> getAllFinancialNeeds() {
        return financialNeedRepository.findAll();
    }
}