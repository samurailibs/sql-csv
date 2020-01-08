package jp.dodododo.sqlcsv.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LtsvUtilTest {

    @Test
    public void get() {
        String s = "a:12:34:54";
        String key = LtsvUtil.getKey(s);
        String value = LtsvUtil.getValue(s);
        assertEquals("a", key);
        assertEquals("12:34:54", value);
    }
}
