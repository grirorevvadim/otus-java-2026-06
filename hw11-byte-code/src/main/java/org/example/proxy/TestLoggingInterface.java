package org.example.proxy;

public interface TestLoggingInterface {
    @Log
    int sum(int...a);

    int max(int...a);
}
