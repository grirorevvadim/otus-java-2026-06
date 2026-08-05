package org.example;


import org.example.scenarios.CalcTest;
import org.example.scenarios.StringTest;

public class Main {
    static void main() {
        TestRunner.run(CalcTest.class);
        TestRunner.run(StringTest.class);
    }

}
