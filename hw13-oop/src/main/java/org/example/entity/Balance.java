package org.example.entity;

import lombok.Data;

@Data
public class Balance {
    private static Balance INSTANCE;
    private int currentBalance;

    public static Balance getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new Balance();
        return INSTANCE;
    }

}
