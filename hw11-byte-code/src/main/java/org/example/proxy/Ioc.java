package org.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Ioc {

    public static TestLoggingInterface createClass() {
        InvocationHandler handler = new MyInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(
                Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class},
                handler
        );
    }
}
