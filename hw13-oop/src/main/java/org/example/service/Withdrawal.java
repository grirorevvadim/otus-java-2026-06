package org.example.service;

import org.example.entity.Banknote;

import java.util.Map;

public interface Withdrawal {
    Map<Banknote, Integer> withdraw(int amount);

    boolean isWithdrawable(int amount);
}
