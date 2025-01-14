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
import pl.lodz.p.ias.io.darczyncy.services.implementations.ItemDonationService;
import pl.lodz.p.ias.io.darczyncy.services.interfaces.IItemDonationService;
import pl.lodz.p.ias.io.darczyncy.utils.I18n;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;
import pl.lodz.p.ias.io.poszkodowani.service.MaterialNeedService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemDonationController implements IItemDonationController {

    private final IItemDonationService itemDonationService;
    private final MaterialNeedService materialNeedService;

    @PreAuthorize("hasAnyRole('DARCZYŃCA')")
    @Override
    public ResponseEntity<?> createItemDonation(ItemDonationCreateDTO itemDonationCreateDTO) {
        ItemDonation itemDonation = itemDonationService.createItemDonation(itemDonationCreateDTO);
        materialNeedService.completeMaterialNeed(itemDonation.getNeed().getId());
        return ResponseEntity.created(URI.create("/donations/%s".formatted(itemDonation.getId()))).build();
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @Override
    public ResponseEntity<?> findItemDonationById(long id) {
        ItemDonation itemDonation;
        try {
            itemDonation = itemDonationService.findItemDonationById(id);
        }
        catch (ItemDonationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(convertToOutputDTO(itemDonation));
    }

    private ItemDonationOutputDTO convertToOutputDTO(ItemDonation itemDonation) {
        MaterialNeed materialNeed = materialNeedService.getMaterialNeedById(itemDonation.getNeed().getId())
                .orElseThrow(() -> new DonationBaseException(I18n.MATERIAL_NEED_NOT_FOUND_EXCEPTION));
        return ItemDonationMapper.toOutputDTO(itemDonation, materialNeed);
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @Override
    public ResponseEntity<?> findAllItemDonations() {
        List<ItemDonation> foundItemDonations = itemDonationService.findAllItemDonations();
        if (foundItemDonations.isEmpty()) return ResponseEntity.noContent().build();
        List<ItemDonationOutputDTO> outputDTOS = foundItemDonations.stream().map(this::convertToOutputDTO).toList();
        return ResponseEntity.ok().body(outputDTOS);
    }

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

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'WOLONTARIUSZ', 'PRZEDSTAWICIEL_WŁADZ')")
    @Override
    public ResponseEntity<?> findAllItemDonationsByDonorId(long donorId) {
        List<ItemDonation> foundItemDonations = itemDonationService.findAllItemDonationsByDonorId(donorId);
        if (foundItemDonations.isEmpty()) return ResponseEntity.noContent().build();
        List<ItemDonationOutputDTO> outputDTOS = foundItemDonations.stream().map(this::convertToOutputDTO).toList();
        return ResponseEntity.ok().body(outputDTOS);
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'WOLONTARIUSZ', 'PRZEDSTAWICIEL_WŁADZ')")
    @Override
    public ResponseEntity<?> findAllItemDonationsWarehouseId(long warehouseId) {
        List<ItemDonation> foundItemDonations = itemDonationService.findAllItemDonationsByWarehouseId(warehouseId);
        if (foundItemDonations.isEmpty()) return ResponseEntity.noContent().build();
        List<ItemDonationOutputDTO> outputDTOS = foundItemDonations.stream().map(this::convertToOutputDTO).toList();
        return ResponseEntity.ok().body(outputDTOS);
    }

    @PreAuthorize("hasAnyRole('DARCZYŃCA')")
    @Override
    public ResponseEntity<?> findAllItemDonationsByCurrentUser() {
        List<ItemDonation> foundItemDonations = itemDonationService.findAllByCurrentUser();
        if (foundItemDonations.isEmpty()) return ResponseEntity.noContent().build();
        List<ItemDonationOutputDTO> outputDTOS = foundItemDonations.stream().map(this::convertToOutputDTO).toList();
        return ResponseEntity.ok().body(outputDTOS);
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'WOLONTARIUSZ', 'PRZEDSTAWICIEL_WŁADZ')")
    @Override
    public ResponseEntity<?> updateItemDonation(long id, ItemDonationUpdateDTO itemDonationUpdateDTO) {
        ItemDonation itemDonation = ItemDonationMapper.fromUpdateDTO(itemDonationUpdateDTO);
        ItemDonation updatedItem = itemDonationService.updateItemDonation(id, itemDonation);
        return ResponseEntity.ok(convertToOutputDTO(updatedItem));
    }

    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'WOLONTARIUSZ', 'PRZEDSTAWICIEL_WŁADZ')")
    @Override
    public ResponseEntity<?> deleteItemDonationById(long id) {
        itemDonationService.deleteItemDonationById(id);
        return ResponseEntity.noContent().build();
    }

}
