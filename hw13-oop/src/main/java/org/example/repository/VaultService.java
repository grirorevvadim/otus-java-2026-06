package org.example.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Balance;
import org.example.entity.Banknote;

import java.util.Map;

@Slf4j
public class VaultService {

    public int getCash(Banknote b) {
        return Vault.getINSTANCE().getCash().getOrDefault(b, 0);
    }

    public void addCash(Banknote b, int amount) {
        if (amount < 0) {
            log.error("Amount wasn't recognized");
            throw new IllegalArgumentException("Amount must be non-negative: " + amount);
        }
        if (amount > Vault.MAX) {
            log.error("Amount is too high for ATM operation");
            throw new IllegalArgumentException("Amount is too high: " + amount);
        }
        if (((b.getValue() * amount) + Balance.getINSTANCE().getCurrentBalance()) > Vault.MAX_CAPACITY) {
            log.error("ATM storage is full");
            throw new IllegalArgumentException("ATM storage is full");
        }
        log.info("Received {} of Banknote:{}", amount, b);
        Vault.getINSTANCE().getCash()
                .put(b, Vault.getINSTANCE().getCash().getOrDefault(b, 0) + amount);
        Balance.getINSTANCE().setCurrentBalance(
                Balance.getINSTANCE().getCurrentBalance() + (amount * b.getValue()));
    }

    public void removeCash(Map<Banknote, Integer> toRemove) {
        toRemove.forEach((note, count) -> {
            log.info("Removed {} of Banknote:{}", count, note);
            Balance.getINSTANCE().setCurrentBalance(Balance.getINSTANCE().getCurrentBalance() - (note.getValue() * count));
            Vault.getINSTANCE().getCash().merge(note, -count, Integer::sum);
        });
    }

    public void vaultInit() {
        for (Banknote b : Banknote.values()) {
            Vault.getINSTANCE().getCash().put(b, 10);
        }
    }
}
