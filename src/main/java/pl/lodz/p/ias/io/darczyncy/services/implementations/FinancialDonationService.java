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

/**
 * Klasa implementująca logikę biznesową dla darowizn finansowych.
 * Odpowiada za zarządzanie darowiznami finansowymi, w tym ich tworzenie, wyszukiwanie i generowanie potwierdzeń.
 */
@RequiredArgsConstructor
@Service
public class FinancialDonationService implements IFinancialDonationService {

    /**
     * Repozytorium do zarządzania danymi darowizn finansowych.
     */
    private final FinancialDonationRepository financialDonationRepository;

    /**
     * Repozytorium do zarządzania kontami użytkowników.
     */
    private final AccountRepository accountRepository;

    /**
     * Repozytorium do zarządzania potrzebami finansowymi.
     */
    private final FinancialNeedRepository financialNeedRepository;

    /**
     * Repozytorium do zarządzania magazynami.
     */
    private final WarehouseRepository warehouseRepository;

    /**
     * Obiekt do generowania certyfikatów dla darowizn.
     */
    private final CertificateProvider certificateProvider = new CertificateProvider();

    /**
     * Tworzy nową darowiznę finansową.
     *
     * @param dto Obiekt DTO zawierający dane nowej darowizny.
     * @return Utworzona darowizna finansowa.
     * @throws DonationBaseException W przypadku błędnych danych wejściowych.
     * @throws PaymentFailedException W przypadku niepowodzenia płatności.
     */
    @Override
    public FinancialDonation createDonation(FinancialDonationCreateDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account donor = accountRepository.findByUsername(authentication.getName());
        FinancialNeed need = financialNeedRepository.findById(dto.needId())
                .orElseThrow(() -> new DonationBaseException(I18n.FINANCIAL_NEED_NOT_FOUND_EXCEPTION));

        try {
            FinancialDonation.Currency.valueOf(dto.currency());
        } catch (IllegalArgumentException e) {
            throw new DonationBaseException(I18n.DONATION_CURRENCY_INVALID_VALUE_EXCEPTION);
        }

        FinancialDonation financialDonation = new FinancialDonation(
                donor,
                need,
                null,
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

    /**
     * Znajduje darowiznę po identyfikatorze.
     *
     * @param id Identyfikator darowizny.
     * @return Znaleziona darowizna finansowa.
     * @throws FinancialDonationNotFoundException Jeśli darowizna nie zostanie znaleziona.
     */
    @Override
    public FinancialDonation findById(long id) {
        return financialDonationRepository.findById(id).orElseThrow(FinancialDonationNotFoundException::new);
    }

    /**
     * Znajduje wszystkie darowizny dla danego darczyńcy.
     *
     * @param donorId Identyfikator darczyńcy.
     * @return Lista darowizn finansowych.
     * @throws DonationBaseException Jeśli darczyńca nie zostanie znaleziony.
     */
    @Override
    public List<FinancialDonation> findAllByDonorId(long donorId) {
        accountRepository.findById(donorId).orElseThrow(() -> new DonationBaseException(I18n.DONOR_NOT_FOUND_EXCEPTION));
        return financialDonationRepository.findAllByDonor_Id(donorId);
    }

    /**
     * Znajduje wszystkie darowizny powiązane z magazynem.
     *
     * @param warehouseId Identyfikator magazynu.
     * @return Lista darowizn finansowych powiązanych z magazynem.
     * @throws DonationBaseException Jeśli magazyn nie zostanie znaleziony.
     */
    @Override
    public List<FinancialDonation> findAllByWarehouseId(long warehouseId) {
        warehouseRepository.findById(warehouseId).orElseThrow(() -> new DonationBaseException(I18n.WAREHOUSE_NOT_FOUND_EXCEPTION));
        return financialDonationRepository.findAllByWarehouseId(warehouseId);
    }

    /**
     * Znajduje wszystkie darowizny dla bieżącego użytkownika.
     *
     * @return Lista darowizn finansowych bieżącego użytkownika.
     */
    @Override
    public List<FinancialDonation> findAllForCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Current user: " + auth.getName());
        Account currentUser = accountRepository.findByUsername(auth.getName());
        return financialDonationRepository.findAllByDonor_Id(currentUser.getId());
    }

    /**
     * Tworzy plik PDF z potwierdzeniem darowizny.
     *
     * @param language Język potwierdzenia.
     * @param donationId Identyfikator darowizny.
     * @return Plik PDF jako tablica bajtów.
     * @throws FinancialDonationNotFoundException Jeśli darowizna nie zostanie znaleziona.
     */
    @Override
    public byte[] createConfirmationPdf(String language, long donationId) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        FinancialDonation financialDonation = financialDonationRepository.findByIdAndDonor_Username(
                        donationId, currentUserName)
                .orElseThrow(FinancialDonationNotFoundException::new);
        // todo check resource status
        return certificateProvider.generateFinancialCertificate(financialDonation.getDonor(), financialDonation, language);
    }

    /**
     * Pobiera wszystkie darowizny.
     *
     * @return Lista wszystkich darowizn finansowych.
     */
    @Override
    public List<FinancialDonation> findAll() {
        return financialDonationRepository.findAll();
    }

}
