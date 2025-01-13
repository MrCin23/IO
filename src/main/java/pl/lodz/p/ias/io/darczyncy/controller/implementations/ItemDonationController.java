package pl.lodz.p.ias.io.darczyncy.controller.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.ias.io.darczyncy.controller.interfaces.IItemDonationController;
import pl.lodz.p.ias.io.darczyncy.dto.create.ItemDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.dto.output.ItemDonationOutputDTO;
import pl.lodz.p.ias.io.darczyncy.dto.update.ItemDonationUpdateDTO;
import pl.lodz.p.ias.io.darczyncy.exceptions.ItemDonationNotFoundException;
import pl.lodz.p.ias.io.darczyncy.mappers.ItemDonationMapper;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;
import pl.lodz.p.ias.io.darczyncy.services.interfaces.IItemDonationService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemDonationController implements IItemDonationController {

    private final IItemDonationService itemDonationService;

    @Override
    public ResponseEntity<?> createItemDonation(ItemDonationCreateDTO itemDonationCreateDTO) {
        ItemDonation itemDonation = itemDonationService.createItemDonation(itemDonationCreateDTO);
        ItemDonationOutputDTO outputDTO = ItemDonationMapper.toOutputDTO(itemDonation);
        return ResponseEntity.created(URI.create("/donations/%s".formatted(outputDTO.id()))).body(outputDTO);
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
        ItemDonationOutputDTO outputDTO = ItemDonationMapper.toOutputDTO(itemDonation);
        return ResponseEntity.ok().body(outputDTO);
    }

    @Override
    public ResponseEntity<?> findAllItemDonations() {
        List<ItemDonation> foundItemDonations = itemDonationService.findAllItemDonations();
        if (foundItemDonations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(foundItemDonations.stream().map(ItemDonationMapper::toOutputDTO).toList());
    }

    @Override
    public ResponseEntity<?> getConfirmationDonationById(long id) {
        byte[] pdfBytes = itemDonationService.createConfirmationPdf(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(pdfBytes.length);
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename("confirmation.pdf").build();
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }

    @Override
    public ResponseEntity<?> findAllItemDonationsByDonorId(long donorId) {
        List<ItemDonation> foundItemDonations = itemDonationService.findItemDonationsByDonorId(donorId);
        if (foundItemDonations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(foundItemDonations.stream().map(ItemDonationMapper::toOutputDTO).toList());
    }

    @Override
    public ResponseEntity<?> updateItemDonation(long id, ItemDonationUpdateDTO itemDonationUpdateDTO) {
        ItemDonation itemDonation = ItemDonationMapper.fromUpdateDTO(itemDonationUpdateDTO);
        ItemDonation updatedItem = itemDonationService.updateItemDonation(id, itemDonation);
        return ResponseEntity.ok(ItemDonationMapper.toOutputDTO(updatedItem));
    }

    @Override
    public ResponseEntity<?> deleteItemDonationById(long id) {
        itemDonationService.deleteItemDonationById(id);
        return ResponseEntity.noContent().build();
    }

}
