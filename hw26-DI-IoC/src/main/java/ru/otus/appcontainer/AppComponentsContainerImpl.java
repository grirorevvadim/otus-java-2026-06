package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;


@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        List<Method> methods = Arrays.stream(configClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(s -> s.getAnnotation(AppComponent.class).order()))
                .toList();

        for (Method m : methods) {
            String name = m.getAnnotation(AppComponent.class).name();
            if (appComponentsByName.containsKey(name))
                throw new IllegalStateException("Duplicate component name: " + name);
            Object[] params = Arrays.stream(m.getParameterTypes())
                    .map(this::getAppComponent)
                    .toArray();
            Object component;
            try {
                Object newInstance = configClass.getDeclaredConstructor().newInstance();
                component = m.invoke(newInstance, params);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            appComponents.add(component);
            appComponentsByName.put(name, component);
        }


        // You code here...
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> components = appComponents.stream()
                .filter(componentClass::isInstance)
                .toList();
        if (components.isEmpty())
            throw new NoSuchElementException("No component of type " + componentClass.getName());
        if (components.size() > 1)
            throw new IllegalStateException("More than one component of type " + componentClass.getName());
        return componentClass.cast(components.getFirst());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(String componentName) {
        Object component = appComponentsByName.get(componentName);
        if (component == null) {
            throw new NoSuchElementException("No component with name " + componentName);
        }
        return (C) component;
    }
}
