package jp.dodododo.sqlcsv.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CharsetUtilTest {
    @Test
    public void getCharset() throws IOException {
        String charset = CharsetUtil.getCharset("src/test/resources/charset/utf8.txt");
        assertEquals("UTF-8", charset);

        charset = CharsetUtil.getCharset("src/test/resources/charset/sjis.txt");
        assertEquals("SHIFT_JIS", charset);

        charset = CharsetUtil.getCharset("src/test/resources/charset/euc-jp.txt");
        assertEquals("EUC-JP", charset);
    }
}
