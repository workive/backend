package app.teamwize.api.base.util;

public class StringUtils {
    public static String toSnakeCase(String className) {
        return className.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}
