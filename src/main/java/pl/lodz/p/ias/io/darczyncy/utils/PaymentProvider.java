package pl.lodz.p.ias.io.darczyncy.utils;

import java.util.concurrent.ThreadLocalRandom;

public class PaymentProvider {

    public boolean processPayment() {
        int russianRoulette = ThreadLocalRandom.current().nextInt(0, 100 + 1);
        return russianRoulette != 13 && russianRoulette != 69;
    }
}
