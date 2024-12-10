package pl.lodz.p.ias.io.darczyncy.exceptions;
import pl.lodz.p.ias.io.darczyncy.utils.I18n;

public class ItemDonationNotFoundException extends DonationBaseException {
    public ItemDonationNotFoundException() {
        super(I18n.ITEM_DONATION_NOT_FOUND_EXCEPTION);
    }
}
