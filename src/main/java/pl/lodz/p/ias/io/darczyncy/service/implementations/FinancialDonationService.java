package pl.lodz.p.ias.io.darczyncy.service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.darczyncy.dto.create.FinancialDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.exceptions.FinancialDonationNotFoundException;
import pl.lodz.p.ias.io.darczyncy.exceptions.PaymentFailedException;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;
import pl.lodz.p.ias.io.darczyncy.repository.FinancialDonationRepository;
import pl.lodz.p.ias.io.darczyncy.service.interfaces.IFinancialDonationService;
import pl.lodz.p.ias.io.darczyncy.utils.PaymentProvider;

@RequiredArgsConstructor
@Service
public class FinancialDonationService implements IFinancialDonationService {

    private final FinancialDonationRepository financialDonationRepository;

    @Override
    public FinancialDonation createFinancialDonation(FinancialDonationCreateDTO dto) {
        FinancialDonation financialDonation = new FinancialDonation(
                dto.donor(),
                dto.need(),
                dto.warehouseId(),
                dto.amount(),
                dto.calculatedVAT(),
                dto.currency()
        );
        PaymentProvider paymentProvider = new PaymentProvider();
        boolean isPaymentSucceed = paymentProvider.processPayment();

        if (isPaymentSucceed) {
            return financialDonationRepository.save(financialDonation);
        }
        throw new PaymentFailedException();
    }

    @Override
    public FinancialDonation findFinancialDonationById(long id) {
        FinancialDonation financialDonation = financialDonationRepository.findById(id);
        if (financialDonation == null) {
            throw new FinancialDonationNotFoundException();
        }
        return financialDonation;
    }

}
