package pl.lodz.p.ias.io.darczyncy.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.darczyncy.dto.create.ItemDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.exceptions.DonationBaseException;
import pl.lodz.p.ias.io.darczyncy.exceptions.ItemDonationNotFoundException;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;
import pl.lodz.p.ias.io.darczyncy.providers.CertificateProvider;
import pl.lodz.p.ias.io.darczyncy.repositories.ItemDonationRepository;
import pl.lodz.p.ias.io.darczyncy.services.interfaces.IItemDonationService;
import pl.lodz.p.ias.io.darczyncy.utils.I18n;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;
import pl.lodz.p.ias.io.poszkodowani.repository.MaterialNeedRepository;

import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.AccountRepository;
import pl.lodz.p.ias.io.zasoby.model.Warehouse;
import pl.lodz.p.ias.io.zasoby.repository.WarehouseRepository;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Klasa ItemDonationService implementuje logikę biznesową dla operacji związanych z darowiznami rzeczowymi.
 * Obsługuje tworzenie, aktualizację, usuwanie oraz wyszukiwanie darowizn.
 * Jest oznaczona adnotacją @Service, co czyni ją komponentem Springa.
 */
@RequiredArgsConstructor
@Service
public class ItemDonationService implements IItemDonationService {

    /**
     * Repozytorium do zarządzania danymi darowizn rzeczowych.
     */
    private final ItemDonationRepository itemDonationRepository;

    /**
     * Repozytorium do zarządzania potrzebami materialnymi.
     */
    private final MaterialNeedRepository materialNeedRepository;

    /**
     * Repozytorium do zarządzania magazynami.
     */
    private final WarehouseRepository warehouseRepository;

    /**
     * Repozytorium do zarządzania kontami użytkowników.
     */
    private final AccountRepository accountRepository;

    /**
     * Obiekt do generowania certyfikatów dla darowizn.
     */
    private final CertificateProvider certificateProvider = new CertificateProvider();

    /**
     * Tworzy nową darowiznę na podstawie danych z DTO.
     *
     * @param dto obiekt zawierający dane nowej darowizny.
     * @return utworzona darowizna.
     * @throws DonationBaseException jeśli kategoria darowizny jest nieprawidłowa lub potrzeba materialna nie istnieje.
     */
    @Override
    public ItemDonation createItemDonation(ItemDonationCreateDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account donor = accountRepository.findByUsername(auth.getName());
        MaterialNeed need = materialNeedRepository.findById(dto.needId())
                .orElseThrow(() -> new DonationBaseException(I18n.MATERIAL_NEED_NOT_FOUND_EXCEPTION));

        try {
            ItemDonation.ItemCategory.valueOf(dto.category());
        } catch (IllegalArgumentException e) {
            throw new DonationBaseException("Invalid category value");
        }

        List<Warehouse> warehouses = warehouseRepository.findAll();
        Random rand = new Random();
        Warehouse selectedWarehouse = warehouses.get(rand.nextInt(warehouses.size()));

        ItemDonation itemDonation = new ItemDonation(
                donor,
                need,
                dto.name(),
                dto.resourceQuantity(),
                selectedWarehouse.getId(),
                ItemDonation.ItemCategory.valueOf(dto.category()),
                dto.description(),
                LocalDate.now()
        );
        return itemDonationRepository.save(itemDonation);
    }

    /**
     * Znajduje darowiznę na podstawie identyfikatora.
     *
     * @param id identyfikator darowizny.
     * @return znaleziona darowizna.
     * @throws ItemDonationNotFoundException jeśli darowizna nie zostanie znaleziona.
     */
    @Override
    public ItemDonation findById(long id) {
        return itemDonationRepository.findById(id).orElseThrow(ItemDonationNotFoundException::new);
    }

    /**
     * Pobiera listę wszystkich darowizn.
     *
     * @return lista darowizn.
     */
    @Override
    public List<ItemDonation> findAllItemDonations() {
        return itemDonationRepository.findAll();
    }

    /**
     * Pobiera listę darowizn wykonanych przez określonego darczyńcę.
     *
     * @param donorId identyfikator darczyńcy.
     * @return lista darowizn wykonanych przez danego darczyńcę.
     * @throws DonationBaseException jeśli darczyńca nie zostanie znaleziony.
     */
    @Override
    public List<ItemDonation> findAllByDonorId(long donorId) {
        accountRepository.findById(donorId).orElseThrow(() -> new DonationBaseException(I18n.DONOR_NOT_FOUND_EXCEPTION));
        return itemDonationRepository.findAllByDonor_Id(donorId);
    }

    /**
     * Pobiera listę darowizn przechowywanych w określonym magazynie.
     *
     * @param warehouseId identyfikator magazynu.
     * @return lista darowizn w danym magazynie.
     * @throws DonationBaseException jeśli magazyn nie zostanie znaleziony.
     */
    @Override
    public List<ItemDonation> findAllByWarehouseId(long warehouseId) {
        warehouseRepository.findById(warehouseId).orElseThrow(() -> new DonationBaseException(I18n.WAREHOUSE_NOT_FOUND_EXCEPTION));
        return itemDonationRepository.findAllByWarehouseId(warehouseId);
    }

    /**
     * Pobiera listę darowizn powiązanych z aktualnie zalogowanym użytkownikiem.
     *
     * @return lista darowizn użytkownika.
     */
    @Override
    public List<ItemDonation> findAllByCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account currentUser = accountRepository.findByUsername(auth.getName());
        return itemDonationRepository.findAllByDonor_Id(currentUser.getId());
    }

    /**
     * Generuje potwierdzenie darowizny w formacie PDF.
     *
     * @param language język dokumentu.
     * @param donationId identyfikator darowizny.
     * @return potwierdzenie w formie bajtów PDF.
     * @throws ItemDonationNotFoundException jeśli darowizna nie zostanie znaleziona.
     */
    @Override
    public byte[] createConfirmationPdf(String language, long donationId) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        ItemDonation itemDonation = itemDonationRepository.findByIdAndDonor_Username(donationId, currentUserName)
                .orElseThrow(ItemDonationNotFoundException::new);
        return certificateProvider.generateItemCertificate(itemDonation.getDonor(), itemDonation, language);
    }

    /**
     * Aktualizuje dane istniejącej darowizny.
     *
     * @param id identyfikator darowizny.
     * @param updatedItemDonation obiekt zaktualizowanej darowizny.
     * @return zaktualizowana darowizna.
     * @throws ItemDonationNotFoundException jeśli darowizna nie zostanie znaleziona.
     */
    @Override
    public ItemDonation update(long id, ItemDonation updatedItemDonation) {
        ItemDonation foundDonation = itemDonationRepository.findById(id).orElseThrow(ItemDonationNotFoundException::new);

        List<Field> fields = new ArrayList<>();
        getAllFields(fields, foundDonation.getClass());

        fields = fields.stream().filter(field -> {
            try {
                field.setAccessible(true);
                return field.get(updatedItemDonation) != null && !field.getName().equals("id");
            } catch (IllegalAccessException e) {
                throw new DonationBaseException(e.getMessage());
            }
        }).toList();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                field.set(foundDonation, field.get(updatedItemDonation));
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                throw new DonationBaseException(e.getMessage());
            }
        }
        return itemDonationRepository.save(foundDonation);
    }

    /**
     * Usuwa darowiznę na podstawie identyfikatora.
     *
     * @param id identyfikator darowizny.
     * @throws ItemDonationNotFoundException jeśli darowizna nie zostanie znaleziona.
     */
    @Override
    public void deleteById(Long id) {
        ItemDonation itemDonation = itemDonationRepository.findById(id).orElseThrow(ItemDonationNotFoundException::new);
        itemDonationRepository.delete(itemDonation);
    }

    /**
     * Pobiera wszystkie pola klasy (łącznie z polami klas nadrzędnych).
     *
     * @param fields lista pól do wypełnienia.
     * @param type klasa, której pola mają być pobrane.
     */
    private static void getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }
    }
}
