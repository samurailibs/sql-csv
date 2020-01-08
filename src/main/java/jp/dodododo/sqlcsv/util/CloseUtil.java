package jp.dodododo.sqlcsv.util;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CloseUtil {
    public static void close(Closeable c) throws IOException {
        if (c == null) {
            return;
        }
        c.close();
    }

    public static void close(Connection c) throws SQLException {
        if (c == null) {
            return;
        }
        c.close();
    }

    public static void close(Statement s) throws SQLException {
        if (s == null) {
            return;
        }
        s.close();
    }
}
