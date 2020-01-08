package jp.dodododo.sqlcsv.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextUtil {
    public static String readText(String filePath) throws IOException {
        InputStream inputStream = null;
        try {
            String charset = CharsetUtil.getCharset(filePath);
            inputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder text = new StringBuilder(1024);
            while ((line = bufferedReader.readLine()) != null) {
                text.append(line);
                text.append("\r\n");
            }
            return text.toString();
        } finally {
            CloseUtil.close(inputStream);
        }
    }
}
