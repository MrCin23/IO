package pl.lodz.p.ias.io.darczyncy.services.implementations;

import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.darczyncy.dto.create.FinancialDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.exceptions.DonationBaseException;
import pl.lodz.p.ias.io.darczyncy.exceptions.FinancialDonationNotFoundException;
import pl.lodz.p.ias.io.darczyncy.exceptions.PaymentFailedException;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;
import pl.lodz.p.ias.io.darczyncy.providers.CertificateProvider;
import pl.lodz.p.ias.io.darczyncy.repositories.FinancialDonationRepository;
import pl.lodz.p.ias.io.darczyncy.services.interfaces.IFinancialDonationService;
import pl.lodz.p.ias.io.darczyncy.utils.I18n;
import pl.lodz.p.ias.io.darczyncy.utils.PaymentProvider;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.poszkodowani.repository.FinancialNeedRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.AccountRepository;
import pl.lodz.p.ias.io.zasoby.model.Warehouse;
import pl.lodz.p.ias.io.zasoby.repository.WarehouseRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class FinancialDonationService implements IFinancialDonationService {

    private final FinancialDonationRepository financialDonationRepository;
    private final AccountRepository accountRepository;
    private final FinancialNeedRepository financialNeedRepository;
    private final WarehouseRepository warehouseRepository;

    private final CertificateProvider certificateProvider = new CertificateProvider();

    @Override
    public FinancialDonation createFinancialDonation(FinancialDonationCreateDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account donor = accountRepository.findByUsername(authentication.getName());
        FinancialNeed need = financialNeedRepository.findById(dto.needId())
                .orElseThrow(() -> new DonationBaseException(I18n.FINANCIAL_NEED_NOT_FOUND_EXCEPTION));

        try {
            FinancialDonation.Currency.valueOf(dto.currency());
        } catch (IllegalArgumentException e) {
            throw new DonationBaseException(I18n.DONATION_CURRENCY_INVALID_VALUE_EXCEPTION);
        }

        List<Warehouse> warehouses = warehouseRepository.findAll();
        // Losowanie magazynu w którym zostanie umieszczona darowizna
        Random rand = new Random();
        Warehouse selectedWarehouse = warehouses.get(rand.nextInt(warehouses.size()));

        FinancialDonation financialDonation = new FinancialDonation(
                donor,
                need,
                selectedWarehouse.getId(),
                dto.amount(),
                FinancialDonation.Currency.valueOf(dto.currency()),
                LocalDate.now()
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
        return financialDonationRepository.findById(id).orElseThrow(FinancialDonationNotFoundException::new);
    }

    @Override
    public List<FinancialDonation> findAllFinancialDonationByDonorId(long donorId) {
        accountRepository.findById(donorId).orElseThrow(() -> new DonationBaseException(I18n.DONOR_NOT_FOUND_EXCEPTION));
        return financialDonationRepository.findAllByDonor_Id(donorId);
    }

    @Override
    public List<FinancialDonation> findAllFinancialDonationByWarehouseId(long warehouseId) {
        warehouseRepository.findById(warehouseId).orElseThrow(() -> new DonationBaseException(I18n.WAREHOUSE_NOT_FOUND_EXCEPTION));
        return financialDonationRepository.findAllByWarehouseId(warehouseId);
    }

    @Override
    public List<FinancialDonation> findAllFinancialDonationForCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Current user: " + auth.getName());
        Account currentUser = accountRepository.findByUsername(auth.getName());
        return financialDonationRepository.findAllByDonor_Id(currentUser.getId());
    }

    @Override
    public byte[] createConfirmationPdf(String language, long donationId) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        FinancialDonation financialDonation = financialDonationRepository.findByIdAndDonor_Username(
                donationId, currentUserName)
                .orElseThrow(FinancialDonationNotFoundException::new);
        return certificateProvider.generateFinancialCertificate(financialDonation.getDonor(), financialDonation, language);
    }

    @Override
    public List<FinancialDonation> findAll() {
        return financialDonationRepository.findAll();
    }

}
