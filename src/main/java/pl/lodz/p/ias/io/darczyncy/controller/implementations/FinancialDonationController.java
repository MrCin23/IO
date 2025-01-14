package pl.lodz.p.ias.io.darczyncy.controller.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.ias.io.darczyncy.controller.interfaces.IFinancialDonationController;
import pl.lodz.p.ias.io.darczyncy.dto.create.FinancialDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.dto.exception.ExceptionOutputDTO;
import pl.lodz.p.ias.io.darczyncy.dto.output.FinancialDonationOutputDTO;
import pl.lodz.p.ias.io.darczyncy.exceptions.DonationBaseException;
import pl.lodz.p.ias.io.darczyncy.exceptions.FinancialDonationNotFoundException;
import pl.lodz.p.ias.io.darczyncy.exceptions.PaymentFailedException;
import pl.lodz.p.ias.io.darczyncy.mappers.FinancialDonationMapper;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;
import pl.lodz.p.ias.io.darczyncy.services.interfaces.IFinancialDonationService;
import pl.lodz.p.ias.io.darczyncy.utils.I18n;
import pl.lodz.p.ias.io.poszkodowani.model.FinancialNeed;
import pl.lodz.p.ias.io.poszkodowani.repository.FinancialNeedRepository;
import pl.lodz.p.ias.io.poszkodowani.service.FinancialNeedService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FinancialDonationController implements IFinancialDonationController {

    private final IFinancialDonationService financialDonationService;
    private final FinancialNeedRepository financialNeedRepository;
    private final FinancialNeedService financialNeedService;

    @PreAuthorize("hasAnyRole('DARCZYŃCA')")
    @Override
    public ResponseEntity<?> createFinancialDonation(FinancialDonationCreateDTO financialDonationCreateDTO) {
        FinancialDonation financialDonation;
        try {
            financialDonation = financialDonationService.createFinancialDonation(financialDonationCreateDTO);
            financialNeedService.updateFinancialNeedCollectionStatus(financialDonation.getNeed().getId(),
                    financialDonation.getAmount());
        }
        catch (PaymentFailedException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        return ResponseEntity.created(URI.create("/donations/%s".formatted(financialDonation.getId()))).build();
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @Override
    public ResponseEntity<?> findFinancialDonationById(long id) {
        FinancialDonation financialDonation;
        try {
            financialDonation = financialDonationService.findFinancialDonationById(id);
        }
        catch (FinancialDonationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(convertToOutputDTO(financialDonation));
    }

    FinancialDonationOutputDTO convertToOutputDTO(FinancialDonation financialDonation) {
        FinancialNeed financialNeed = financialNeedService.getFinancialNeedById(financialDonation.getNeed().getId())
                .orElseThrow( () -> new DonationBaseException(I18n.FINANCIAL_NEED_NOT_FOUND_EXCEPTION));
        return FinancialDonationMapper.toOutputDTO(financialDonation, financialNeed);
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'WOLONTARIUSZ', 'PRZEDSTAWICIEL_WŁADZ')")
    @Override
    public ResponseEntity<?> findAllFinancialDonationsByDonorId(long id) {
        List<FinancialDonation> financialDonations = financialDonationService.findAllFinancialDonationByDonorId(id);
        if (financialDonations.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(financialDonations.stream().map(this::convertToOutputDTO));
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'WOLONTARIUSZ', 'PRZEDSTAWICIEL_WŁADZ')")
    @Override
    public ResponseEntity<?> findAllFinancialDonationsByWarehouseId(long id) {
        List<FinancialDonation> financialDonations = financialDonationService.findAllFinancialDonationByWarehouseId(id);
        if (financialDonations.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(financialDonations.stream().map(this::convertToOutputDTO));
    }

    @PreAuthorize("hasAnyRole('DARCZYŃCA')")
    @Override
    public ResponseEntity<?> findAllFinancialDonationsForCurrentUser() {
        List<FinancialDonation> financialDonations = financialDonationService.findAllFinancialDonationForCurrentUser();
        if (financialDonations.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(financialDonations.stream().map(this::convertToOutputDTO));
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @Override
    public ResponseEntity<?> findAll() {
        List<FinancialDonation> financialDonations = financialDonationService.findAll();
        if (financialDonations.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(financialDonations.stream().map(this::convertToOutputDTO));
    }

    @PreAuthorize("hasAnyRole('DARCZYŃCA')")
    @Override
    public ResponseEntity<?> getConfirmationDonationById(String language, long id) {
        byte[] pdfBytes;
        String lang = language.substring(0,2);
        try {
            pdfBytes = financialDonationService.createConfirmationPdf(lang, id);
        } catch (FinancialDonationNotFoundException e) {
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
}
