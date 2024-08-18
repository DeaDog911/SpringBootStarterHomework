package org.deadog.springbootstarterhomework.util;

public class StringUtil {
    public static String camelToSnake(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        char c = str.charAt(0);
        stringBuilder.append(Character.toLowerCase(c));
        for (int i = 1; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (Character.isUpperCase(ch)) {
                stringBuilder.append("-");
                stringBuilder.append(Character.toLowerCase(ch));
            } else {
                stringBuilder.append(ch);
            }
        }
        return stringBuilder.toString();
    }
}
