package org.example.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MyInvocationHandler implements InvocationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyInvocationHandler.class);
    private final TestLoggingInterface testLoggingInterface;

    public MyInvocationHandler(TestLoggingInterface testLoggingInterface) {
        this.testLoggingInterface = testLoggingInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Log.class)) {
            LOGGER.info("Executed method: {}, param: {}", method.getName(), formatArgs(args));
        }
        return method.invoke(testLoggingInterface, args);
    }

    private String formatArgs(Object[] args) {
        if (args == null || args.length == 0) {
            return "";
        }
        if (args.length == 1 && args[0] != null && args[0].getClass().isArray()) {
            return arrayToString(args[0]);
        }
        return Arrays.stream(args)
                .map(a -> (a != null && a.getClass().isArray()) ? arrayToString(a) : String.valueOf(a))
                .collect(Collectors.joining(", "));
    }

    private String arrayToString(Object array) {
        int length = Array.getLength(array);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(Array.get(array, i));
        }
        return sb.toString();
    }
}
