package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Balance;
import org.example.entity.Banknote;
import org.example.repository.VaultService;
import org.example.service.Withdrawal;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;

@Slf4j
public class CashWithdrawer implements Withdrawal {

    private final VaultService vaultService;

    public CashWithdrawer() {
        this.vaultService = new VaultService();
    }

    @Override
    public Map<Banknote, Integer> withdraw(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative: " + amount);
        }
        if (!isWithdrawable(amount)) {
            throw new RuntimeException("Недостаточно средств в банкомате");
        }
        Map<Banknote, Integer> toRemove = Map.of();
        if (isWithdrawable(amount)) {
            Banknote[] sortedDesc = Arrays.stream(Banknote.values())
                    .sorted(Comparator.comparingInt(Banknote::getValue).reversed())
                    .toArray(Banknote[]::new);

            toRemove = new EnumMap<>(Banknote.class);
            int remaining = amount;

            for (Banknote note : sortedDesc) {
                int available = vaultService.getCash(note);
                int noteValue = note.getValue();
                int maxByValue = Math.toIntExact(remaining / noteValue);
                int count = Math.min(available, maxByValue);

                if (count > 0) {
                    toRemove.put(note, count);
                    remaining -= count * noteValue;
                }
            }
            if (remaining > 0) {
                throw new IllegalStateException(
                        "Insufficient banknotes to withdraw exact amount: " + amount);
            }
            vaultService.removeCash(toRemove);
        } else {
            log.error("There is not enough cash in ATM");
        }
        return toRemove;
    }

    @Override
    public boolean isWithdrawable(int amount) {
        return Balance.getINSTANCE().getCurrentBalance() >= amount;
    }
}
