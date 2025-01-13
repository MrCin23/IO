package pl.lodz.p.ias.io.darczyncy.controller.interfaces;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.darczyncy.dto.create.FinancialDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.utils.DonationConstants;

@RequestMapping(DonationConstants.APPLICATION_CONTEXT + "/donations/financial")
public interface IFinancialDonationController {

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createFinancialDonation(@Valid @RequestBody FinancialDonationCreateDTO financialDonationCreateDTO);


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> findFinancialDonationById(@PathVariable long id);

    @GetMapping(value = "/donor/{id}/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> findAllFinancialDonationsByDonorId(@PathVariable long id);

    @GetMapping(value = "/warehouse/{id}/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> findAllFinancialDonationsByWarehouseId(@PathVariable("id") long id);

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> findAll();

    @GetMapping(value = "/{id}/confirmation", produces = {MediaType.APPLICATION_PDF_VALUE, MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<?> getConfirmationDonationById(@PathVariable("id") long id);
}
