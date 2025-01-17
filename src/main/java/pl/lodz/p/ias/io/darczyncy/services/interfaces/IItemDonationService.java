package pl.lodz.p.ias.io.darczyncy.services.interfaces;

import pl.lodz.p.ias.io.darczyncy.dto.create.ItemDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;

import java.util.List;

/**
 * Interfejs służący do zarządzania darowiznami rzeczowymi.
 * Zawiera metody umożliwiające tworzenie, wyszukiwanie, usuwanie darowizn oraz generowanie potwierdzeń.
 */
public interface IItemDonationService {

    /**
     * Tworzy darowiznę rzeczową.
     *
     * @param dto Obiekt zawierający dane do utworzenia darowizny.
     * @return Utworzona darowizna rzeczowa.
     */
    ItemDonation createItemDonation(ItemDonationCreateDTO dto, String language);

    /**
     * Wyszukuje darowiznę rzeczową na podstawie jej identyfikatora.
     *
     * @param id Identyfikator darowizny.
     * @return Darowizna rzeczowa o podanym identyfikatorze.
     */
    ItemDonation findById(long id);

    /**
     * Wyszukuje wszystkie darowizny rzeczowe.
     *
     * @return Lista wszystkich darowizn rzeczowych.
     */
    List<ItemDonation> findAllItemDonations();

    /**
     * Wyszukuje wszystkie darowizny rzeczowe zrealizowane przez określonego darczyńcę.
     *
     * @param donorId Identyfikator darczyńcy.
     * @return Lista darowizn rzeczowych przypisanych do darczyńcy.
     */
    List<ItemDonation> findAllByDonorId(long donorId);

    /**
     * Wyszukuje wszystkie darowizny rzeczowe przypisane do określonego magazynu.
     *
     * @param warehouseId Identyfikator magazynu.
     * @return Lista darowizn rzeczowych przypisanych do magazynu.
     */
    List<ItemDonation> findAllByWarehouseId(long warehouseId);

    /**
     * Wyszukuje wszystkie darowizny rzeczowe związane z bieżącym użytkownikiem.
     *
     * @return Lista darowizn rzeczowych powiązanych z bieżącym użytkownikiem.
     */
    List<ItemDonation> findAllByCurrentUser();

    /**
     * Tworzy dokument PDF z potwierdzeniem darowizny rzeczowej.
     *
     * @param language Język, w którym ma być wygenerowane potwierdzenie.
     * @param donationId Identyfikator darowizny.
     * @return Tablica bajtów zawierająca wygenerowany plik PDF.
     */
    byte[] createConfirmationPdf(String language, long donationId);

    /**
     * Aktualizuje istniejącą darowiznę rzeczową.
     *
     * @param id Identyfikator darowizny do zaktualizowania.
     * @param updatedItemDonation Zaktualizowany obiekt darowizny rzeczowej.
     * @return Zaktualizowana darowizna rzeczowa.
     */
    ItemDonation update(long id, ItemDonation updatedItemDonation);

    /**
     * Usuwa darowiznę rzeczową na podstawie jej identyfikatora.
     *
     * @param id Identyfikator darowizny do usunięcia.
     */
    void deleteById(Long id);
}
