package io.bar.beerhub.util;

import io.bar.beerhub.util.factory.EscapeCharsUtil;
import org.apache.commons.lang.StringEscapeUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EscapeCharsUtilImpl implements EscapeCharsUtil {
    @Override
    public <T> T escapeChars(T objectT) {
        Field[] fields = objectT.getClass().getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method getter;

            String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method setter;

            try {
                getter = objectT.getClass().getMethod(getterName);
                setter = objectT.getClass().getMethod(setterName, String.class);
                getter.setAccessible(true);
                setter.setAccessible(true);

                Object currentFieldObject = getter.invoke(objectT);
                if (currentFieldObject == null) {
                    continue;
                }

                String currentFieldValue = currentFieldObject.toString();
                String escapedString = StringEscapeUtils.escapeHtml(currentFieldValue);
                setter.invoke(objectT, escapedString);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                continue;
            }
        }

        return objectT;
    }

    @Override
    public String escapeChars(String string) {
        return StringEscapeUtils.escapeHtml(string);
    }

    @Override
    public <T> T unEscapeChars(T objectT) {
        Field[] fields = objectT.getClass().getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method getter;

            String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method setter;

            try {
                getter = objectT.getClass().getMethod(getterName);
                setter = objectT.getClass().getMethod(setterName, String.class);

                String unEscapedString = StringEscapeUtils.unescapeHtml(getter.invoke(objectT).toString());
                setter.invoke(objectT, unEscapedString);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                continue;
            }
        }

        return objectT;
    }

    @Override
    public String unEscapeChars(String string) {
        return StringEscapeUtils.unescapeHtml(string);
    }
}
