package pl.lodz.p.ias.io.darczyncy.dto.update;

import lombok.Builder;
import pl.lodz.p.ias.io.darczyncy.model.Donation;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;

public record ItemDonationUpdateDTO(
        String name,
        String description,
        ItemDonation.ItemCategory category,
        Donation.AcceptanceStatus acceptanceStatus
) {}
