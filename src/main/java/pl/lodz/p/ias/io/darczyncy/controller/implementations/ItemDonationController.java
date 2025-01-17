package pl.lodz.p.ias.io.darczyncy.controller.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.ias.io.darczyncy.controller.interfaces.IItemDonationController;
import pl.lodz.p.ias.io.darczyncy.dto.create.ItemDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.dto.exception.ExceptionOutputDTO;
import pl.lodz.p.ias.io.darczyncy.dto.output.ItemDonationOutputDTO;
import pl.lodz.p.ias.io.darczyncy.dto.update.ItemDonationUpdateDTO;
import pl.lodz.p.ias.io.darczyncy.exceptions.DonationBaseException;
import pl.lodz.p.ias.io.darczyncy.exceptions.ItemDonationNotFoundException;
import pl.lodz.p.ias.io.darczyncy.mappers.ItemDonationMapper;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;
import pl.lodz.p.ias.io.darczyncy.services.interfaces.IItemDonationService;
import pl.lodz.p.ias.io.darczyncy.utils.I18n;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;
import pl.lodz.p.ias.io.poszkodowani.service.MaterialNeedService;

import java.net.URI;
import java.util.List;

/**
 * Kontroler REST obsługujący operacje związane z darowiznami przedmiotów.
 * Implementuje interfejs {@link IItemDonationController}.
 *
 * <p>Ten kontroler zapewnia funkcjonalności takie jak:
 * - Tworzenie nowych darowizn przedmiotów.
 * - Pobieranie szczegółowych informacji o darowiznach.
 * - Aktualizacja istniejących darowizn.
 * - Usuwanie darowizn.
 * - Generowanie potwierdzeń darowizn w formacie PDF.
 * - Filtrowanie darowizn na podstawie różnych kryteriów.
 *
 * <p>Wymagane role użytkowników są określone dla każdej metody za pomocą adnotacji {@code @PreAuthorize}.
 * Adnotacje zabezpieczają dostęp w zależności od ról użytkowników.
 */
@RequiredArgsConstructor
@RestController
public class ItemDonationController implements IItemDonationController {

    /**
     * Serwis obsługujący logikę biznesową dla darowizn rzeczowych.
     */
    private final IItemDonationService itemDonationService;

    /**
     * Serwis obsługujący potrzeby materialne.
     */
    private final MaterialNeedService materialNeedService;

    /**
     * Tworzy nową darowiznę przedmiotu.
     * @param itemDonationCreateDTO Obiekt zawierający dane do utworzenia darowizny.
     * @return Odpowiedź HTTP z lokalizacją nowo utworzonej darowizny.
     */
    @PreAuthorize("hasAnyRole('DARCZYŃCA')")
    @Override
    public ResponseEntity<?> createItemDonation(String language, ItemDonationCreateDTO itemDonationCreateDTO) {
        ItemDonation itemDonation = itemDonationService.createItemDonation(itemDonationCreateDTO, language.substring(0,2));
        materialNeedService.completeMaterialNeed(itemDonation.getNeed().getId());
        return ResponseEntity.created(URI.create("/donations/%s".formatted(itemDonation.getId()))).build();
    }

    /**
     * Wyszukuje darowiznę przedmiotu na podstawie jej identyfikatora.
     * @param id Identyfikator darowizny.
     * @return Odpowiedź HTTP zawierająca szczegóły darowizny lub kod 404, jeśli darowizna nie istnieje.
     */
    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @Override
    public ResponseEntity<?> findItemDonationById(long id) {
        ItemDonation itemDonation;
        try {
            itemDonation = itemDonationService.findById(id);
        }
        catch (ItemDonationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(convertToOutputDTO(itemDonation));
    }

    /**
     * Konwertuje obiekt {@link ItemDonation} na obiekt {@link ItemDonationOutputDTO}.
     *
     * <p>Metoda pobiera potrzebę materialną powiązaną z darowizną oraz przekształca
     * dane na obiekt DTO, który jest wykorzystywany w odpowiedziach HTTP.</p>
     *
     * @param itemDonation Obiekt darowizny, który ma zostać przekonwertowany.
     * @return Obiekt {@link ItemDonationOutputDTO} zawierający szczegóły darowizny.
     * @throws DonationBaseException jeśli powiązana potrzeba materialna nie zostanie znaleziona.
     */
    private ItemDonationOutputDTO convertToOutputDTO(ItemDonation itemDonation) {
        MaterialNeed materialNeed = materialNeedService.getMaterialNeedById(itemDonation.getNeed().getId())
                .orElseThrow(() -> new DonationBaseException(I18n.MATERIAL_NEED_NOT_FOUND_EXCEPTION));
        return ItemDonationMapper.toOutputDTO(itemDonation, materialNeed);
    }

    /**
     * Pobiera listę wszystkich darowizn przedmiotów.
     * @return Odpowiedź HTTP z listą darowizn lub kod 204, jeśli brak darowizn.
     */
    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @Override
    public ResponseEntity<?> findAllItemDonations() {
        List<ItemDonation> foundItemDonations = itemDonationService.findAllItemDonations();
        if (foundItemDonations.isEmpty()) return ResponseEntity.noContent().build();
        List<ItemDonationOutputDTO> outputDTOS = foundItemDonations.stream().map(this::convertToOutputDTO).toList();
        return ResponseEntity.ok().body(outputDTOS);
    }

    /**
     * Generuje potwierdzenie darowizny w formacie PDF na podstawie jej identyfikatora.
     * @param language Kod języka (np. "pl").
     * @param id Identyfikator darowizny.
     * @return Odpowiedź HTTP z plikiem PDF lub kod 400 w przypadku błędu.
     */
    @PreAuthorize("hasAnyRole('DARCZYŃCA')")
    @Override
    public ResponseEntity<?> getConfirmationDonationById(String language, long id) {
        String lang = language.substring(0, 2);
        byte[] pdfBytes;
        try {
            pdfBytes = itemDonationService.createConfirmationPdf(lang, id);
        } catch (ItemDonationNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ExceptionOutputDTO(e.getMessage()));
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(pdfBytes.length);
        headers.setContentType(MediaType.APPLICATION_PDF);
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename("confirmation.pdf").build();
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }

    /**
     * Pobiera listę darowizn powiązanych z określonym darczyńcą.
     * @param donorId Identyfikator darczyńcy.
     * @return Odpowiedź HTTP z listą darowizn lub kod 204, jeśli brak darowizn.
     */
    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'WOLONTARIUSZ', 'PRZEDSTAWICIEL_WŁADZ')")
    @Override
    public ResponseEntity<?> findAllItemDonationsByDonorId(long donorId) {
        List<ItemDonation> foundItemDonations = itemDonationService.findAllByDonorId(donorId);
        if (foundItemDonations.isEmpty()) return ResponseEntity.noContent().build();
        List<ItemDonationOutputDTO> outputDTOS = foundItemDonations.stream().map(this::convertToOutputDTO).toList();
        return ResponseEntity.ok().body(outputDTOS);
    }

    /**
     * Pobiera listę darowizn powiązanych z określonym magazynem.
     * @param warehouseId Identyfikator magazynu.
     * @return Odpowiedź HTTP z listą darowizn lub kod 204, jeśli brak darowizn.
     */
    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'WOLONTARIUSZ', 'PRZEDSTAWICIEL_WŁADZ')")
    @Override
    public ResponseEntity<?> findAllItemDonationsWarehouseId(long warehouseId) {
        List<ItemDonation> foundItemDonations = itemDonationService.findAllByWarehouseId(warehouseId);
        if (foundItemDonations.isEmpty()) return ResponseEntity.noContent().build();
        List<ItemDonationOutputDTO> outputDTOS = foundItemDonations.stream().map(this::convertToOutputDTO).toList();
        return ResponseEntity.ok().body(outputDTOS);
    }

    /**
     * Pobiera listę darowizn powiązanych z aktualnie zalogowanym użytkownikiem.
     * @return Odpowiedź HTTP z listą darowizn lub kod 204, jeśli brak darowizn.
     */
    @PreAuthorize("hasAnyRole('DARCZYŃCA')")
    @Override
    public ResponseEntity<?> findAllItemDonationsByCurrentUser() {
        List<ItemDonation> foundItemDonations = itemDonationService.findAllByCurrentUser();
        if (foundItemDonations.isEmpty()) return ResponseEntity.noContent().build();
        List<ItemDonationOutputDTO> outputDTOS = foundItemDonations.stream().map(this::convertToOutputDTO).toList();
        return ResponseEntity.ok().body(outputDTOS);
    }

    /**
     * Aktualizuje istniejącą darowiznę przedmiotu.
     * @param id Identyfikator darowizny do aktualizacji.
     * @param itemDonationUpdateDTO Obiekt zawierający dane do aktualizacji darowizny.
     * @return Odpowiedź HTTP z danymi zaktualizowanej darowizny.
     */
    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'WOLONTARIUSZ', 'PRZEDSTAWICIEL_WŁADZ')")
    @Override
    public ResponseEntity<?> updateItemDonation(long id, ItemDonationUpdateDTO itemDonationUpdateDTO) {
        ItemDonation itemDonation = ItemDonationMapper.fromUpdateDTO(itemDonationUpdateDTO);
        ItemDonation updatedItem = itemDonationService.update(id, itemDonation);
        return ResponseEntity.ok(convertToOutputDTO(updatedItem));
    }

    /**
     * Usuwa darowiznę przedmiotu na podstawie jej identyfikatora.
     * @param id Identyfikator darowizny do usunięcia.
     * @return Odpowiedź HTTP z kodem 204 po pomyślnym usunięciu.
     */
    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'WOLONTARIUSZ', 'PRZEDSTAWICIEL_WŁADZ')")
    @Override
    public ResponseEntity<?> deleteItemDonationById(long id) {
        itemDonationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
