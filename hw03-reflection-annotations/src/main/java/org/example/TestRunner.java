package org.example;

import org.example.annotations.After;
import org.example.annotations.Before;
import org.example.annotations.Test;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class TestRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestRunner.class);

    public static void run(Class<?> testClass) {
        HashMap<MethodType, ArrayList<Method>> methods = initMethodsMap();
        LOGGER.info("Class is found: {}", testClass);
        collectAllTests(testClass.getDeclaredMethods(), methods);
        executeAllTests(testClass, methods);
    }

    private static @NonNull HashMap<MethodType, ArrayList<Method>> initMethodsMap() {
        HashMap<MethodType, ArrayList<Method>> methods = new HashMap<>();
        methods.put(MethodType.BEFORE, new ArrayList<>());
        methods.put(MethodType.TEST, new ArrayList<>());
        methods.put(MethodType.AFTER, new ArrayList<>());
        return methods;
    }

    private static void executeAllTests(Class<?> testClass, HashMap<MethodType, ArrayList<Method>> methods) {
        int totalTestAmount = methods.get(MethodType.TEST).size();
        int success = 0;
        int failed = 0;
        for (Method m : methods.get(MethodType.TEST)) {
            try {
                Object instance = testClass.getDeclaredConstructor().newInstance();
                executeFixture(instance, methods.get(MethodType.BEFORE));
                try {
                    m.invoke(instance);
                    success++;
                } finally {
                    executeFixture(instance, methods.get(MethodType.AFTER));
                }
            } catch (Exception e) {
                LOGGER.error("Test: {} failed with exception: {}", m.getName(), e.toString());
                failed++;
            }
        }
        printStatistics(totalTestAmount, success, failed);
    }

    private static void printStatistics(int totalTestAmount, int success, int failed) {
        LOGGER.info("--------------------------");
        LOGGER.info("Total Tests Amount: {}", totalTestAmount);
        LOGGER.info("Passed Tests Amount: {}", success);
        LOGGER.info("Failed Tests Amount: {}", failed);
        LOGGER.info("--------------------------");
    }

    private static void executeFixture(Object instance, ArrayList<Method> methods) throws InvocationTargetException, IllegalAccessException {
        for (Method m : methods) {
            m.invoke(instance);
        }
    }

    private static void collectAllTests(Method[] declaredMethods, HashMap<MethodType, ArrayList<Method>> methods) {
        for (Method m : declaredMethods) {
            for (Annotation a : m.getDeclaredAnnotations()) {
                if (a.annotationType().equals(Before.class))
                    methods.get(MethodType.BEFORE).add(m);
                if (a.annotationType().equals(Test.class))
                    methods.get(MethodType.TEST).add(m);
                if (a.annotationType().equals(After.class))
                    methods.get(MethodType.AFTER).add(m);
            }
        }
    }
}