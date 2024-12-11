package pl.lodz.p.ias.io.darczyncy.controller.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import pl.lodz.p.ias.io.darczyncy.controller.interfaces.IDonationController;
import pl.lodz.p.ias.io.darczyncy.dto.create.FinancialDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.dto.create.ItemDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.dto.output.FinancialDonationOutputDTO;
import pl.lodz.p.ias.io.darczyncy.dto.output.ItemDonationOutputDTO;
import pl.lodz.p.ias.io.darczyncy.exceptions.FinancialDonationNotFoundException;
import pl.lodz.p.ias.io.darczyncy.exceptions.ItemDonationNotFoundException;
import pl.lodz.p.ias.io.darczyncy.exceptions.PaymentFailedException;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;
import pl.lodz.p.ias.io.darczyncy.services.interfaces.IFinancialDonationService;
import pl.lodz.p.ias.io.darczyncy.services.interfaces.IItemDonationService;
import pl.lodz.p.ias.io.darczyncy.utils.DonationMapper;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class DonationController implements IDonationController {

    private final IFinancialDonationService financialDonationService;
    private final IItemDonationService itemDonationService;

    @Override
    public ResponseEntity<?> createFinancialDonation(FinancialDonationCreateDTO financialDonationCreateDTO) {
        FinancialDonation financialDonation;
        try {
            financialDonation = financialDonationService.createFinancialDonation(financialDonationCreateDTO);
        }
        catch (PaymentFailedException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        FinancialDonationOutputDTO outputDTO = DonationMapper.toFinancialDonationOutputDTO(financialDonation);
        return ResponseEntity.created(URI.create("/donations/%s".formatted(outputDTO.id()))).body(outputDTO);
    }

    @Override
    public ResponseEntity<?> createItemDonation(ItemDonationCreateDTO itemDonationCreateDTO) {
        ItemDonation itemDonation = itemDonationService.createItemDonation(itemDonationCreateDTO);
        ItemDonationOutputDTO outputDTO = DonationMapper.toItemDonationOutputDTO(itemDonation);
        return ResponseEntity.created(URI.create("/donations/%s".formatted(outputDTO.id()))).body(outputDTO);
    }

    @Override
    public ResponseEntity<?> findFinancialDonationById(long id) {
        FinancialDonation financialDonation;
        try {
            financialDonation = financialDonationService.findFinancialDonationById(id);
        }
        catch (FinancialDonationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        FinancialDonationOutputDTO outputDTO = DonationMapper.toFinancialDonationOutputDTO(financialDonation);
        return ResponseEntity.ok().body(outputDTO);
    }

    @Override
    public ResponseEntity<?> findItemDonationById(long id) {
        ItemDonation itemDonation;
        try {
            itemDonation = itemDonationService.findItemDonationById(id);
        }
        catch (ItemDonationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        ItemDonationOutputDTO outputDTO = DonationMapper.toItemDonationOutputDTO(itemDonation);
        return ResponseEntity.ok().body(outputDTO);
    }
}
