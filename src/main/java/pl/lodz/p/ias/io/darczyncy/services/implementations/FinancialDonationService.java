package pl.lodz.p.ias.io.darczyncy.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.darczyncy.dto.create.FinancialDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.exceptions.FinancialDonationNotFoundException;
import pl.lodz.p.ias.io.darczyncy.exceptions.PaymentFailedException;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;
import pl.lodz.p.ias.io.darczyncy.repositories.FinancialDonationRepository;
import pl.lodz.p.ias.io.darczyncy.services.interfaces.IFinancialDonationService;
import pl.lodz.p.ias.io.darczyncy.utils.PaymentProvider;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.poszkodowani.repository.FinancialNeedRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.UserRepository;

@RequiredArgsConstructor
@Service
public class FinancialDonationService implements IFinancialDonationService {

    private final FinancialDonationRepository financialDonationRepository;
    private final UserRepository userRepository;
    private final FinancialNeedRepository financialNeedRepository;

    @Override
    public FinancialDonation createFinancialDonation(FinancialDonationCreateDTO dto) {

        Account donor = userRepository.findById(dto.donorId()).orElse(null);
        FinancialNeed need = financialNeedRepository.findById(dto.needId()).orElse(null);

        FinancialDonation financialDonation = new FinancialDonation(
                donor,
                need,
                dto.warehouseId(),
                dto.amount(),
                dto.calculatedVAT(),
                FinancialDonation.Currency.valueOf(dto.currency())
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
        FinancialDonation financialDonation = financialDonationRepository.findById(id).orElse(null);
        if (financialDonation == null) {
            throw new FinancialDonationNotFoundException();
        }
        return financialDonation;
    }

}
