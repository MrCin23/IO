package pl.lodz.p.ias.io.darczyncy.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.darczyncy.dto.create.ItemDonationCreateDTO;
import pl.lodz.p.ias.io.darczyncy.exceptions.DonationBaseException;
import pl.lodz.p.ias.io.darczyncy.exceptions.ItemDonationNotFoundException;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;
import pl.lodz.p.ias.io.darczyncy.providers.CertificateProvider;
import pl.lodz.p.ias.io.darczyncy.repositories.ItemDonationRepository;
import pl.lodz.p.ias.io.darczyncy.services.interfaces.IItemDonationService;
import pl.lodz.p.ias.io.darczyncy.utils.I18n;
import pl.lodz.p.ias.io.poszkodowani.model.MaterialNeed;
import pl.lodz.p.ias.io.poszkodowani.repository.MaterialNeedRepository;

import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.UserRepository;
import pl.lodz.p.ias.io.zasoby.model.Warehouse;
import pl.lodz.p.ias.io.zasoby.repository.WarehouseRepository;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemDonationService implements IItemDonationService {

    private final ItemDonationRepository itemDonationRepository;

    private final MaterialNeedRepository materialNeedRepository;

    private final WarehouseRepository warehouseRepository;

    private final UserRepository userRepository;

    private final CertificateProvider certificateProvider = new CertificateProvider();

    @Override
    public ItemDonation createItemDonation(ItemDonationCreateDTO dto) {
        Account donor = userRepository.findById(dto.donorId())
                .orElseThrow(() -> new DonationBaseException(I18n.DONOR_NOT_FOUND_EXCEPTION));
        MaterialNeed need = materialNeedRepository.findById(dto.needId())
                .orElseThrow(() -> new DonationBaseException(I18n.MATERIAL_NEED_NOT_FOUND_EXCEPTION));
        Warehouse warehouse = warehouseRepository.findById(dto.warehouseId())
                .orElseThrow(() -> new DonationBaseException(I18n.WAREHOUSE_NOT_FOUND_EXCEPTION));

        try {
            ItemDonation.ItemCategory.valueOf(dto.category());
        } catch (IllegalArgumentException e) {
            throw new DonationBaseException("Invalid category value");
        }

        ItemDonation itemDonation = new ItemDonation(
                donor,
                need,
                dto.itemName(),
                dto.resourceQuantity(),
                warehouse.getId(),
                ItemDonation.ItemCategory.valueOf(dto.category()),
                dto.description(),
                LocalDate.now()
        );
        return itemDonationRepository.save(itemDonation);
    }

    @Override
    public ItemDonation findItemDonationById(long id) {
        ItemDonation itemDonation = itemDonationRepository.findById(id).orElse(null);
        if (itemDonation == null) {
            throw new ItemDonationNotFoundException();
        }
        return itemDonation;
    }

    @Override
    public List<ItemDonation> findAllItemDonations() {
        return itemDonationRepository.findAll();
    }

    @Override
    public List<ItemDonation> findItemDonationsByDonorId(long donorId) {
        userRepository.findById(donorId).orElseThrow(() -> new DonationBaseException(I18n.DONOR_NOT_FOUND_EXCEPTION));
        return itemDonationRepository.findAllByDonor_Id(donorId);
    }

    private static void getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }
    }

    @Override
    public byte[] createConfirmationPdf(long donationId) {
        ItemDonation itemDonation = itemDonationRepository.findById(donationId)
                .orElseThrow( ItemDonationNotFoundException::new);
        return certificateProvider.generateItemCertificate(itemDonation.getDonor(), itemDonation);
    }

    @Override
    public ItemDonation updateItemDonation(long id, ItemDonation updatedItemDonation) {
        ItemDonation foundDonation = itemDonationRepository.findById(id).orElseThrow(ItemDonationNotFoundException::new);

        List<Field> fields = new ArrayList<>();

        getAllFields(fields, foundDonation.getClass());

        fields = fields.stream().filter( (field) ->
        {
            try {
                field.setAccessible(true);
                return field.get(updatedItemDonation) != null && !field.getName().equals("id");
            } catch (IllegalAccessException e) {
                // todo change exception
                throw new RuntimeException(e);
            }
        }).toList();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                field.set(foundDonation, field.get(updatedItemDonation));
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                // todo change exception
                throw new RuntimeException(e);
            }
        }
        return itemDonationRepository.save(foundDonation);
    }

    @Override
    public void deleteItemDonationById(Long id) {
        ItemDonation itemDonation = itemDonationRepository.findById(id).orElseThrow(ItemDonationNotFoundException::new);
        itemDonationRepository.delete(itemDonation);
    }
}
