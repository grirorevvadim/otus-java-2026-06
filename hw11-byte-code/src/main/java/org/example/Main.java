package org.example;

import org.example.proxy.Ioc;
import org.example.proxy.TestLoggingInterface;

public class Main {
    static void main() {
        TestLoggingInterface testLogging = Ioc.createClass();
        int r1 = testLogging.sum(32);
        r1 = testLogging.sum(2, 3);
        r1 = testLogging.sum(32, 32, 32);
        int r2 = testLogging.max(31, 32, 33);
    }
}
