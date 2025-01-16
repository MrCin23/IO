package pl.lodz.p.ias.io.darczyncy.services.interfaces;

import pl.lodz.p.ias.io.darczyncy.dto.create.FinancialDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;

import java.util.List;

public interface IFinancialDonationService {
    FinancialDonation createDonation(FinancialDonationCreateDTO dto);

    FinancialDonation findById(long id);

    List<FinancialDonation> findAllByDonorId(long donorId);

    List<FinancialDonation> findAllByWarehouseId(long warehouseId);

    List<FinancialDonation> findAllForCurrentUser();

    byte[] createConfirmationPdf(String language, long donationId);

    List<FinancialDonation> findAll();
}
