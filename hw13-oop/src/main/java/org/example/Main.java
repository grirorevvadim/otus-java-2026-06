package org.example;


import lombok.extern.slf4j.Slf4j;
import org.example.entity.ATM;
import org.example.entity.Banknote;

import java.util.Map;

@Slf4j
public class Main {
    static void main() {
        ATM atm = new ATM();
        atm.prepareATM();
        log.info(atm.checkBalance());
        Map<Banknote, Integer> cash = atm.getCash(1700);
        log.info("cash: {}", cash);
        log.info(atm.checkBalance());
        atm.putCash(Banknote.THOUSAND, 1);
        atm.putCash(Banknote.FIVE_HUNDRED, 1);
        atm.putCash(Banknote.HUNDRED, 1);
        atm.putCash(Banknote.TEN, 9);
        atm.putCash(Banknote.ONE, 10);
        log.info(atm.checkBalance());
    }
}
