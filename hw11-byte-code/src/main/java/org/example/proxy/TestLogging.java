package org.example.proxy;


import java.util.Arrays;
import java.util.OptionalInt;

public class TestLogging implements TestLoggingInterface {

    @Override
    public int sum(int... param) {
        return Arrays.stream(param).sum();
    }

    @Override
    public int max(int... a) {
        OptionalInt i = Arrays.stream(a).max();
        if (i.isPresent())
            return i.getAsInt();
        else return 0;
    }
}
