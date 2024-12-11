package pl.lodz.p.ias.io.poszkodowani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.poszkodowani.repository.FinancialNeedRepository;

import java.util.Date;
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
        // TODO: logika odpowiedzialana za sprawdzenie czy użytkownik oraz punkt na mapie istnieje

        financialNeed.setCreationDate(new Date());
        financialNeed.setPriority(1);
        financialNeed.setStatus("New");
        financialNeed.setCollectionStatus(0);

        return financialNeedRepository.save(financialNeed);
    }

    public Optional<FinancialNeed> getFinancialNeedById(Long id) {
        return financialNeedRepository.findById(id);
    }

    public List<FinancialNeed> getAllFinancialNeeds() {
        return financialNeedRepository.findAll();
    }
}