package pl.lodz.p.ias.io.darczyncy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;

import java.util.List;
import java.util.Optional;

/**
 * Interfejs repozytorium do obsługi operacji związanych z darowiznami rzeczowymi.
 * <p>
 * Repozytorium umożliwia wykonywanie podstawowych operacji CRUD na obiektach typu {@link ItemDonation}.
 * </p>
 *
 * @see JpaRepository
 * @see ItemDonation
 */
@Repository
public interface ItemDonationRepository extends JpaRepository<ItemDonation, Long> {

    /**
     * Zwraca listę darowizn rzeczowych złożonych przez określonego darczyńcę.
     *
     * @param donorId Identyfikator darczyńcy
     * @return Lista darowizn rzeczowych złożonych przez darczyńcę o podanym identyfikatorze
     */
    List<ItemDonation> findAllByDonor_Id(Long donorId);

    /**
     * Zwraca listę darowizn rzeczowych przypisanych do określonego magazynu.
     *
     * @param warehouseId Identyfikator magazynu
     * @return Lista darowizn rzeczowych przypisanych do magazynu o podanym identyfikatorze
     */
    List<ItemDonation> findAllByWarehouseId(Long warehouseId);

    /**
     * Zwraca darowiznę rzeczową o określonym identyfikatorze, złożoną przez darczyńcę o podanej nazwie użytkownika.
     *
     * @param id Identyfikator darowizny
     * @param donorUsername Nazwa użytkownika darczyńcy
     * @return Opcjonalna darowizna rzeczowa, która pasuje do podanych parametrów
     */
    Optional<ItemDonation> findByIdAndDonor_Username(long id, String donorUsername);
}
