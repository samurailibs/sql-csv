package jp.dodododo.sqlcsv.util;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CharsetUtil {
    public static String getCharset(String filePath) throws IOException {
        UniversalDetector detector = new UniversalDetector(null);
        String charset;
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buff = new byte[8024];
            int size;
            while ((size = in.read(buff)) != -1) {
                byteArrayOutputStream.write(buff, 0, size);
            }
            byte[] data = byteArrayOutputStream.toByteArray();
            detector.handleData(data, 0, data.length);
            detector.dataEnd();
            charset = detector.getDetectedCharset();
            detector.reset();

            if (charset == null) {
                charset = "UTF8";
            }
            return charset;
        } finally {
            CloseUtil.close(in);
        }
    }
}
