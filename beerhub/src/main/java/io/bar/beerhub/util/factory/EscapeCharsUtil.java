package io.bar.beerhub.util.factory;

public interface EscapeCharsUtil {
    <T> T escapeChars(T objectT);

    String escapeChars(String string);

    <T> T unEscapeChars(T objectT);

    String unEscapeChars(String string);
}
