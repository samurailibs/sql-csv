package jp.dodododo.sqlcsv.util;

public class LtsvUtil {
    public static String getKey(String s) {
        return s.substring(0, s.indexOf(':'));
    }

    public static String getValue(String s) {
        return s.substring(s.indexOf(':') + 1);
    }
}
