package org.example.service.impl;

import org.example.entity.Balance;
import org.example.service.Getting;

public class CashGetter implements Getting {
    @Override
    public int getBalance() {
        return Balance.getINSTANCE().getCurrentBalance();
    }
}
