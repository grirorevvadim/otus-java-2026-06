package org.example.service.impl;

import org.example.entity.Banknote;
import org.example.repository.VaultService;
import org.example.service.Receiving;


public class CashReceiver implements Receiving {

    private final VaultService vaultService;

    public CashReceiver() {
        this.vaultService = new VaultService();
    }

    @Override
    public void receiveOne(Integer amount) {
        vaultService.addCash(Banknote.ONE, amount);
    }

    @Override
    public void receiveTen(Integer amount) {
        vaultService.addCash(Banknote.TEN, amount);
    }

    @Override
    public void receiveFifty(Integer amount) {
        vaultService.addCash(Banknote.FIFTY, amount);
    }

    @Override
    public void receiveHundred(Integer amount) {
        vaultService.addCash(Banknote.HUNDRED, amount);
    }

    @Override
    public void receiveFiveHundred(Integer amount) {
        vaultService.addCash(Banknote.FIVE_HUNDRED, amount);
    }

    @Override
    public void receiveThousand(Integer amount) {
        vaultService.addCash(Banknote.THOUSAND, amount);
    }
}
