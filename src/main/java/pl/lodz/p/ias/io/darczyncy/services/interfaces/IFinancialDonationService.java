package pl.lodz.p.ias.io.darczyncy.services.interfaces;

import pl.lodz.p.ias.io.darczyncy.dto.create.FinancialDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;

import java.util.List;

/**
 * Interfejs służący do zarządzania darowiznami finansowymi.
 * Zawiera metody umożliwiające tworzenie, wyszukiwanie, pobieranie potwierdzeń darowizn oraz zarządzanie darowiznami.
 */
public interface IFinancialDonationService {

    /**
     * Tworzy darowiznę finansową.
     *
     * @param dto Obiekt zawierający dane do utworzenia darowizny.
     * @return Utworzona darowizna finansowa.
     */
    FinancialDonation createFinancialDonation(FinancialDonationCreateDTO dto, String language);

    /**
     * Wyszukuje darowiznę finansową na podstawie jej identyfikatora.
     *
     * @param id Identyfikator darowizny.
     * @return Darowizna finansowa o podanym identyfikatorze.
     */
    FinancialDonation findById(long id);

    /**
     * Wyszukuje wszystkie darowizny finansowe zrealizowane przez określonego darczyńcę.
     *
     * @param donorId Identyfikator darczyńcy.
     * @return Lista darowizn finansowych przypisanych do darczyńcy.
     */
    List<FinancialDonation> findAllByDonorId(long donorId);

    /**
     * Wyszukuje wszystkie darowizny finansowe przypisane do określonego magazynu.
     *
     * @param warehouseId Identyfikator magazynu.
     * @return Lista darowizn finansowych przypisanych do magazynu.
     */
    List<FinancialDonation> findAllByWarehouseId(long warehouseId);

    /**
     * Wyszukuje wszystkie darowizny finansowe związane z bieżącym użytkownikiem.
     *
     * @return Lista darowizn finansowych powiązanych z bieżącym użytkownikiem.
     */
    List<FinancialDonation> findAllForCurrentUser();

    /**
     * Tworzy dokument PDF z potwierdzeniem darowizny finansowej.
     *
     * @param language Język, w którym ma być wygenerowane potwierdzenie.
     * @param donationId Identyfikator darowizny.
     * @return Tablica bajtów zawierająca wygenerowany plik PDF.
     */
    byte[] createConfirmationPdf(String language, long donationId);

    /**
     * Wyszukuje wszystkie darowizny finansowe.
     *
     * @return Lista wszystkich darowizn finansowych.
     */
    List<FinancialDonation> findAll();
}
