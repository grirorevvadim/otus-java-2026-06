package org.example.entity;

import org.example.repository.BalanceService;
import org.example.repository.VaultService;
import org.example.service.Getting;
import org.example.service.Receiving;
import org.example.service.Withdrawal;
import org.example.service.impl.CashGetter;
import org.example.service.impl.CashReceiver;
import org.example.service.impl.CashWithdrawer;

import java.util.Map;


public class ATM {
    private final Getting cashGetter;
    private final Receiving cashReceiver;
    private final Withdrawal cashWithdrawer;
    private final VaultService vaultService;
    private final BalanceService balanceService;

    public ATM() {
        this.balanceService = new BalanceService();
        this.cashGetter = new CashGetter();
        this.cashReceiver = new CashReceiver();
        this.cashWithdrawer = new CashWithdrawer();
        this.vaultService = new VaultService();
    }

    public void prepareATM() {
        this.vaultService.vaultInit();
        this.balanceService.init();
    }


    public Map<Banknote, Integer> getCash(int amount) {
        return cashWithdrawer.withdraw(amount);
    }

    public String checkBalance() {
        return String.valueOf(cashGetter.getBalance());
    }

    public void putCash(Banknote banknoteType, int amount) {
        switch (banknoteType) {
            case ONE -> cashReceiver.receiveOne(amount);
            case TEN -> cashReceiver.receiveTen(amount);
            case FIFTY -> cashReceiver.receiveFifty(amount);
            case HUNDRED -> cashReceiver.receiveHundred(amount);
            case FIVE_HUNDRED -> cashReceiver.receiveFiveHundred(amount);
            case THOUSAND -> cashReceiver.receiveThousand(amount);
        }
    }
}
