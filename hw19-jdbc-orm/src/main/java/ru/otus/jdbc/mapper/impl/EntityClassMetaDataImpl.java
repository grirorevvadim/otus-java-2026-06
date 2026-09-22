package ru.otus.jdbc.mapper.impl;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.model.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        // Ищем конструктор, параметры которого точно соответствуют
        // порядку и типам полей, возвращаемых getAllFields()
        List<Field> allFields = getAllFields();
        Class<?>[] paramTypes = allFields.stream().map(Field::getType).toArray(Class<?>[]::new);
        try {
            return clazz.getConstructor(paramTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(
                    "No constructor found for " + clazz.getName() + " matching field order: " + allFields, e);
        }
    }

    @Override
    public Field getIdField() {
        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(Id.class)) {
                return f;
            }
        }
        return null;
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.asList(clazz.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> fields = new ArrayList<>();
        for (Field f : clazz.getDeclaredFields()) {
            if (!f.isAnnotationPresent(Id.class)) {
                fields.add(f);
            }
        }
        return fields;
    }


}