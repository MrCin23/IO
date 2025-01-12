package pl.lodz.p.ias.io.darczyncy.controller.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.ias.io.darczyncy.controller.interfaces.IFinancialDonationController;
import pl.lodz.p.ias.io.darczyncy.dto.create.FinancialDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.dto.output.FinancialDonationOutputDTO;
import pl.lodz.p.ias.io.darczyncy.exceptions.FinancialDonationNotFoundException;
import pl.lodz.p.ias.io.darczyncy.exceptions.PaymentFailedException;
import pl.lodz.p.ias.io.darczyncy.mappers.FinancialDonationMapper;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;
import pl.lodz.p.ias.io.darczyncy.services.interfaces.IFinancialDonationService;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class FinancialDonationController implements IFinancialDonationController {

    private final IFinancialDonationService financialDonationService;

    @Override
    public ResponseEntity<?> createFinancialDonation(FinancialDonationCreateDTO financialDonationCreateDTO) {
        FinancialDonation financialDonation;
        try {
            financialDonation = financialDonationService.createFinancialDonation(financialDonationCreateDTO);
        }
        catch (PaymentFailedException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        FinancialDonationOutputDTO outputDTO = FinancialDonationMapper.toOutputDTO(financialDonation);
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

        FinancialDonationOutputDTO outputDTO = FinancialDonationMapper.toOutputDTO(financialDonation);
        return ResponseEntity.ok().body(outputDTO);
    }
}
