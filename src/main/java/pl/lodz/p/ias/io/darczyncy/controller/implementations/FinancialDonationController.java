package pl.lodz.p.ias.io.darczyncy.controller.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
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
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public ResponseEntity<?> findAllFinancialDonationsByDonorId(long id) {

        List<FinancialDonation> financialDonations = financialDonationService.findFinancialDonationByDonorId(id);

        if (financialDonations.isEmpty()) return ResponseEntity.notFound().build();

        List<FinancialDonationOutputDTO> outputDTOS = financialDonations.stream()
                .map(FinancialDonationMapper::toOutputDTO).toList();

        return ResponseEntity.ok(outputDTOS);
    }

    @Override
    public ResponseEntity<?> findAll() {
        List<FinancialDonation> financialDonations = financialDonationService.findAll();
        List<FinancialDonationOutputDTO> financialDonationOutputDTOS = financialDonations.stream()
                .map(FinancialDonationMapper::toOutputDTO).toList();
        return ResponseEntity.ok(financialDonationOutputDTOS);
    }

    @Override
    public ResponseEntity<?> getConfirmationDonationById(long id) {
        byte[] pdfBytes = financialDonationService.createConfirmationPdf(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(pdfBytes.length);
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename("confirmation.pdf").build();
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}
