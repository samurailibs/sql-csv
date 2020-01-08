package jp.dodododo.sqlcsv.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static String[] FORMATS = {
            // TODO add
            //
            "yyyy-MM-dd'T'HH:mm:ss.SSSSZ",//
            "yyyy/MM/dd'T'HH:mm:ss.SSSSZ",//
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ",//
            "yyyy/MM/dd'T'HH:mm:ss.SSSZ",//
            "yyyy-MM-dd'T'HH:mm:ss.SSZ",//
            "yyyy/MM/dd'T'HH:mm:ss.SSZ",//
            "yyyy-MM-dd HH:mm:ss",//
            "yyyy/MM/dd HH:mm:ss",//
            "yyyy-MM-dd",//
            "yyyy/MM/dd",//
    };

    public static Date convert(String value) {
        if (value == null) {
            return null;
        }
        for (String f : FORMATS) {
            try {
                Date date = new SimpleDateFormat(f).parse(value);
                return new Timestamp(date.getTime());
            } catch (ParseException ignore) {
            }
        }
        throw new RuntimeException(value);
    }
}
