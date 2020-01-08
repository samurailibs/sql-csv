package jp.dodododo.sqlcsv.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextUtilTest {
    @Test
    public void readText() throws IOException {
        String text = TextUtil.readText("src/test/resources/charset/utf8.txt");
        assertEquals("あいうえお\r\n", text);
    }
}
