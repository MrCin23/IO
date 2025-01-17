package pl.lodz.p.ias.io.darczyncy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;

import java.util.List;
import java.util.Optional;

/**
 * Interfejs repozytorium do obsługi operacji związanych z darowiznami finansowymi.
 * <p>
 * Repozytorium umożliwia wykonywanie podstawowych operacji CRUD na obiektach typu {@link FinancialDonation}.
 * </p>
 *
 * @see JpaRepository
 * @see FinancialDonation
 */
@Repository
public interface FinancialDonationRepository extends JpaRepository<FinancialDonation, Long> {

    /**
     * Zwraca listę darowizn finansowych złożonych przez określonego darczyńcę.
     *
     * @param id Identyfikator darczyńcy
     * @return Lista darowizn finansowych złożonych przez darczyńcę o podanym identyfikatorze
     */
    List<FinancialDonation> findAllByDonor_Id(Long id);

    /**
     * Zwraca listę darowizn finansowych przypisanych do określonego magazynu.
     *
     * @param id Identyfikator magazynu
     * @return Lista darowizn finansowych przypisanych do magazynu o podanym identyfikatorze
     */
    List<FinancialDonation> findAllByWarehouseId(Long id);

    /**
     * Zwraca darowiznę finansową o określonym identyfikatorze, złożoną przez darczyńcę o podanej nazwie użytkownika.
     *
     * @param id Identyfikator darowizny
     * @param donorUsername Nazwa użytkownika darczyńcy
     * @return Opcjonalna darowizna finansowa, która pasuje do podanych parametrów
     */
    Optional<FinancialDonation> findByIdAndDonor_Username(long id, String donorUsername);
}
