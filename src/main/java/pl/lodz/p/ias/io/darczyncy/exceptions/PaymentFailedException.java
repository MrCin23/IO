package pl.lodz.p.ias.io.darczyncy.exceptions;

import pl.lodz.p.ias.io.darczyncy.utils.I18n;

public class PaymentFailedException extends DonationBaseException {
    public PaymentFailedException() {
        super(I18n.FINANCIAL_DONATION_PAYMENT_FAILED_EXCEPTION);
    }
}
