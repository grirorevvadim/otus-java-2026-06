package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сохраняет объект в базу, читает объект из базы
 */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(
                connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), this::readOne);
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(
                        connection, entitySQLMetaData.getSelectByIdSql(), List.of(), this::readAll)
                .orElseThrow(() -> new RuntimeException("Unexpected error reading all entities"));
    }

    @Override
    public long insert(Connection connection, T entity) {
        List<Object> params = fieldValues(entityClassMetaData.getFieldsWithoutId(), entity);
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), params);
    }

    @Override
    public void update(Connection connection, T entity) {
        List<Object> params = fieldValues(entityClassMetaData.getFieldsWithoutId(), entity);
        params.add(getFieldValue(entityClassMetaData.getIdField(), entity));
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);
    }

    private T readOne(ResultSet rs) {
        try {
            return rs.next() ? createEntity(rs) : null;
        } catch (SQLException e) {
            throw new RuntimeException("Error reading entity from ResultSet", e);
        }
    }

    private List<T> readAll(ResultSet rs) {
        List<T> result = new ArrayList<>();
        try {
            while (rs.next()) {
                result.add(createEntity(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error reading entities from ResultSet", e);
        }
    }

    private T createEntity(ResultSet rs) {
        try {
            List<Field> allFields = entityClassMetaData.getAllFields();
            Object[] args = new Object[allFields.size()];
            for (int i = 0; i < allFields.size(); i++) {
                Field field = allFields.get(i);
                args[i] = rs.getObject(field.getName(), field.getType());
            }
            return entityClassMetaData.getConstructor().newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException("Error instantiating entity " + entityClassMetaData.getName(), e);
        }
    }

    private List<Object> fieldValues(List<Field> fields, T entity) {
        List<Object> values = new ArrayList<>();
        for (Field field : fields) {
            values.add(getFieldValue(field, entity));
        }
        return values;
    }

    private Object getFieldValue(Field field, T entity) {
        try {
            field.setAccessible(true);
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot read field " + field.getName(), e);
        }
    }

}
