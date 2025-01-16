package pl.lodz.p.ias.io.darczyncy.dto.update;

import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;
import pl.lodz.p.ias.io.zasoby.utils.ResourceStatus;

/**
 * DTO do aktualizacji darowizny rzeczowej.
 * Zawiera dane, które mogą być zaktualizowane w istniejącej darowiznie rzeczowej.
 */
public record ItemDonationUpdateDTO(
        String name,
        String description,
        ItemDonation.ItemCategory category,
        ResourceStatus resourceStatus
) {}
