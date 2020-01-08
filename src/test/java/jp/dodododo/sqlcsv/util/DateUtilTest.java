package jp.dodododo.sqlcsv.util;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DateUtilTest {

    @Test
    public void convert() {
        Date date = DateUtil.convert("2019-11-21T17:10:45.537+0900");
        assertNotNull(date);
    }
}
