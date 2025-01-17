package pl.lodz.p.ias.io.darczyncy.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Klasa odpowiedzialna za symulowanie procesu przetwarzania płatności.
 * Płatności są realizowane w sposób losowy, z wyjątkiem sytuacji,
 * w których wynik jest równy 13 lub 69 (tzw. "russian roulette").
 */
public class PaymentProvider {

    /**
     * Metoda symulująca przetwarzanie płatności.
     * Zwraca <code>true</code>, jeżeli płatność została przetworzona pomyślnie,
     * oraz <code>false</code>, jeżeli wystąpił losowy błąd (na przykład wynik "russian roulette").
     *
     * @return <code>true</code>, jeśli płatność została przetworzona pomyślnie; <code>false</code> w przeciwnym przypadku.
     */
    public boolean processPayment() {
        int russianRoulette = ThreadLocalRandom.current().nextInt(0, 100 + 1);
        return russianRoulette != 13 && russianRoulette != 69;
    }
}
