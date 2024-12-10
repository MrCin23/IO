package pl.lodz.p.ias.io.darczyncy.controller.interfaces;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.darczyncy.dto.create.FinancialDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.dto.create.ItemDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.utils.GeneralConstants;


@RequestMapping(GeneralConstants.APPLICATION_CONTEXT + "/donations")
public interface IDonationController {

    @PostMapping(value = "financial/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createFinancialDonation(@Valid @RequestBody FinancialDonationCreateDTO financialDonationCreateDTO);

    @PostMapping(value = "item/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createItemDonation(@Valid @RequestBody ItemDonationCreateDTO itemDonationCreateDTO);

    @GetMapping(value = "financial/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> findFinancialDonationById(@PathVariable long id);

    @GetMapping(value = "item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> findItemDonationById(@PathVariable long id);

}
