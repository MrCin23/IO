package pl.lodz.p.ias.io.darczyncy.services.interfaces;

import pl.lodz.p.ias.io.darczyncy.dto.create.FinancialDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;

public interface IFinancialDonationService {
    FinancialDonation createFinancialDonation(FinancialDonationCreateDTO dto);

    FinancialDonation findFinancialDonationById(long id);
}
