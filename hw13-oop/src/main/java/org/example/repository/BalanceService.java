package org.example.repository;

import org.example.entity.Balance;
import org.example.entity.Banknote;

public class BalanceService {

    public BalanceService() {
        this.vaultService = new VaultService();
    }

    private final VaultService vaultService;

    public void init() {
        for (Banknote b : Banknote.values()) {
            Balance.getINSTANCE().setCurrentBalance
                    (Balance.getINSTANCE().getCurrentBalance() + (vaultService.getCash(b) * b.getValue()));
        }
    }

}
