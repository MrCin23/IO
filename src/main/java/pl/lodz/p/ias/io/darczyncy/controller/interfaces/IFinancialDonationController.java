package pl.lodz.p.ias.io.darczyncy.controller.interfaces;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.darczyncy.dto.create.FinancialDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.utils.DonationConstants;

/**
 * Interfejs kontrolera dla operacji związanych z darowiznami finansowymi.
 * Zapewnia operacje tworzenia, wyszukiwania oraz uzyskiwania potwierdzeń darowizn finansowych.
 */
@RequestMapping(DonationConstants.APPLICATION_CONTEXT + "/donations/financial")
public interface IFinancialDonationController {

    /**
     * Tworzy nową darowiznę finansową.
     *
     * @param financialDonationCreateDTO Obiekt zawierający dane do utworzenia darowizny finansowej
     * @return Odpowiedź HTTP zawierająca status operacji
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createFinancialDonation(@Valid @RequestBody FinancialDonationCreateDTO financialDonationCreateDTO);

    /**
     * Wyszukuje darowiznę finansową po identyfikatorze.
     *
     * @param id Identyfikator darowizny finansowej
     * @return Odpowiedź HTTP zawierająca szczegóły darowizny finansowej
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> findFinancialDonationById(@PathVariable long id);

    /**
     * Wyszukuje wszystkie darowizny finansowe dla darczyńcy o podanym identyfikatorze.
     *
     * @param id Identyfikator darczyńcy
     * @return Odpowiedź HTTP zawierająca listę darowizn finansowych
     */
    @GetMapping(value = "/donor/{id}/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> findAllFinancialDonationsByDonorId(@PathVariable long id);

    /**
     * Wyszukuje wszystkie darowizny finansowe przypisane do magazynu o podanym identyfikatorze.
     *
     * @param id Identyfikator magazynu
     * @return Odpowiedź HTTP zawierająca listę darowizn finansowych
     */
    @GetMapping(value = "/warehouse/{id}/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> findAllFinancialDonationsByWarehouseId(@PathVariable("id") long id);

    /**
     * Wyszukuje wszystkie darowizny finansowe przypisane do bieżącego użytkownika.
     *
     * @return Odpowiedź HTTP zawierająca listę darowizn finansowych
     */
    @GetMapping(value = "/account/all")
    ResponseEntity<?> findAllFinancialDonationsForCurrentUser();

    /**
     * Wyszukuje wszystkie darowizny finansowe.
     *
     * @return Odpowiedź HTTP zawierająca listę wszystkich darowizn finansowych
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> findAll();

    /**
     * Pobiera potwierdzenie darowizny finansowej w formacie PDF lub JSON.
     *
     * @param language Język, w którym ma być wygenerowane potwierdzenie
     * @param id Identyfikator darowizny finansowej
     * @return Odpowiedź HTTP zawierająca potwierdzenie darowizny
     */
    @GetMapping(value = "/{id}/confirmation", produces = {MediaType.APPLICATION_PDF_VALUE, MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<?> getConfirmationDonationById(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language, @PathVariable("id") long id);
}
