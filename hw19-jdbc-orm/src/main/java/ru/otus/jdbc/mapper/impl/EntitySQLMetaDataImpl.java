package ru.otus.jdbc.mapper.impl;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return "select * from " + tableName();
    }

    @Override
    public String getSelectByIdSql() {
        return "select * from " + tableName() + " where " + idColumn() + " = ?";
    }

    @Override
    public String getInsertSql() {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        String columns = fields.stream().map(Field::getName).collect(Collectors.joining(", "));
        String placeholders = fields.stream().map(f -> "?").collect(Collectors.joining(", "));
        return "insert into " + tableName() + "(" + columns + ") values (" + placeholders + ")";
    }

    @Override
    public String getUpdateSql() {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        String setClause = fields.stream().map(f -> f.getName() + " = ?").collect(Collectors.joining(", "));
        return "update " + tableName() + " set " + setClause + " where " + idColumn() + " = ?";
    }

    private String tableName() {
        return entityClassMetaData.getName().toLowerCase();
    }

    private String idColumn() {
        return entityClassMetaData.getIdField().getName();
    }
}
