package com.leonzaher.objectrestmapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectRestMapper<T extends Object> {

    private AccessLevel accessLevel;

    private List<String> fieldExclusions;

    public ObjectRestMapper(AccessLevel accessLevel, String... fieldExclusions) {
        this.accessLevel = accessLevel;

        this.fieldExclusions = Arrays.asList(fieldExclusions);
    }

    public String convertToParams(T obj) throws IllegalAccessException {
        if (obj == null)
            return "";

        List<Field> fields = getFields(obj);

        StringBuilder result = new StringBuilder("?");

        for (int i = 0; i < fields.size(); i++) {
            result.append(parseField(fields.get(i), obj, i == 0));
        }

        if (result.toString().equals("?"))
            return "";

        return result.toString();
    }

    private String parseField(Field field, T obj, Boolean appendAnd) throws IllegalAccessException {
        field.setAccessible(true);

        return appendAnd ? field.getName() + "=" + field.get(obj) : "&" + field.getName() + "=" + field.get(obj);
    }

    private List<Field> getFields(T obj) {
        List<Field> fields = null;

        switch (accessLevel) {
            case ALL:
                fields = new ArrayList<>(Arrays.asList(obj.getClass().getDeclaredFields()));
                fields.remove(fields.size() - 1);
                break;
            case PUBLIC:
                fields = new ArrayList<>(Arrays.asList(obj.getClass().getFields()));
                break;
            case NOT_PUBLIC:
                final List<Field> exclusions = new ArrayList<>(Arrays.asList(obj.getClass().getFields()));
                fields = new ArrayList<>(Arrays.asList(obj.getClass().getDeclaredFields()));

                fields.removeIf(exclusions::contains);
                fields.remove(fields.size() - 1);
                break;
        }

        fields = exclude(fields);

        return fields;
    }

    private List<Field> exclude(List<Field> fields) {
        fields.removeIf(el -> fieldExclusions.contains(el.getName()));

        return fields;
    }
}
