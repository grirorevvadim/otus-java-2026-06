package org.example.repository;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.Banknote;

import java.util.HashMap;

@Setter
@Getter
public class Vault {
    public static final int MAX = 1000;
    public static final int MAX_CAPACITY = 10000000;
    private HashMap<Banknote, Integer> cash = new HashMap<>();

    private static Vault INSTANCE;

    private Vault() {
    }

    public static Vault getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new Vault();
        return INSTANCE;
    }

}


