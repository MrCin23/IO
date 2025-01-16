package pl.lodz.p.ias.io.darczyncy.controller.interfaces;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.darczyncy.dto.create.ItemDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.dto.update.ItemDonationUpdateDTO;
import pl.lodz.p.ias.io.darczyncy.utils.DonationConstants;

/**
 * Interfejs kontrolera do zarządzania darowiznami przedmiotów.
 * Udostępnia metody umożliwiające operacje na darowiznach przedmiotów, takie jak tworzenie, aktualizowanie, usuwanie,
 * oraz pobieranie informacji o darowiznach.
 */
@RequestMapping(DonationConstants.APPLICATION_CONTEXT + "/donations/item")
public interface IItemDonationController {

    /**
     * Tworzy nową darowiznę przedmiotu.
     *
     * @param itemDonationCreateDTO Obiekt zawierający dane do utworzenia darowizny przedmiotu
     * @return Odpowiedź HTTP z wynikiem operacji
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createItemDonation(@Valid @RequestBody ItemDonationCreateDTO itemDonationCreateDTO);

    /**
     * Znajduje darowiznę przedmiotu na podstawie identyfikatora.
     *
     * @param id Identyfikator darowizny przedmiotu
     * @return Odpowiedź HTTP zawierająca dane darowizny lub błąd, jeśli nie znaleziono
     */
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> findItemDonationById(@PathVariable long id);

    /**
     * Zwraca listę wszystkich darowizn przedmiotów.
     *
     * @return Odpowiedź HTTP zawierająca listę darowizn przedmiotów
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> findAllItemDonations();

    /**
     * Pobiera potwierdzenie darowizny przedmiotu w formacie PDF lub JSON na podstawie identyfikatora.
     *
     * @param language Język akceptowany w odpowiedzi
     * @param id Identyfikator darowizny przedmiotu
     * @return Odpowiedź HTTP zawierająca potwierdzenie darowizny w żądanym formacie
     */
    @GetMapping(value = "/{id}/confirmation", produces = {MediaType.APPLICATION_PDF_VALUE, MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<?> getConfirmationDonationById(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language, @PathVariable("id") long id);

    /**
     * Zwraca wszystkie darowizny przedmiotów dokonane przez określonego darczyńcę.
     *
     * @param donorId Identyfikator darczyńcy
     * @return Odpowiedź HTTP zawierająca listę darowizn przedmiotów
     */
    @GetMapping(value = "/donor/{id}/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> findAllItemDonationsByDonorId(@PathVariable("id") long donorId);

    /**
     * Zwraca wszystkie darowizny przedmiotów dla określonego magazynu.
     *
     * @param donorId Identyfikator magazynu
     * @return Odpowiedź HTTP zawierająca listę darowizn przedmiotów
     */
    @GetMapping(value = "/warehouse/{id}/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> findAllItemDonationsWarehouseId(@PathVariable("id") long donorId);

    /**
     * Zwraca wszystkie darowizny przedmiotów dokonane przez obecnego użytkownika.
     *
     * @return Odpowiedź HTTP zawierająca listę darowizn przedmiotów
     */
    @GetMapping(value = "/account/all")
    ResponseEntity<?> findAllItemDonationsByCurrentUser();

    /**
     * Aktualizuje dane darowizny przedmiotu na podstawie identyfikatora.
     *
     * @param id Identyfikator darowizny przedmiotu
     * @param itemDonationUpdateDTO Obiekt zawierający zaktualizowane dane darowizny przedmiotu
     * @return Odpowiedź HTTP z wynikiem operacji
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> updateItemDonation(@PathVariable("id") long id, @RequestBody ItemDonationUpdateDTO itemDonationUpdateDTO);

    /**
     * Usuwa darowiznę przedmiotu na podstawie identyfikatora.
     *
     * @param id Identyfikator darowizny przedmiotu
     * @return Odpowiedź HTTP z wynikiem operacji
     */
    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> deleteItemDonationById(@PathVariable("id") long id);
}
