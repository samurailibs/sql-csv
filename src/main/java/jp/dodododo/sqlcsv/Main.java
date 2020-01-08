package jp.dodododo.sqlcsv;

import jp.dodododo.sqlcsv.db.DBColumnList;
import jp.dodododo.sqlcsv.exception.ArgumentException;
import jp.dodododo.sqlcsv.util.CharsetUtil;
import jp.dodododo.sqlcsv.util.CloseUtil;
import jp.dodododo.sqlcsv.util.CsvUtil;
import jp.dodododo.sqlcsv.util.DBUtil;
import org.hsqldb.jdbcDriver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class Main {

    public static final String TABLE_NAME = "data";

    public static void main(String... args) throws IOException, SQLException {
        Config config;
        try {
            config = new Config(Arrays.asList(args));
        } catch (ArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1);
            return;
        }

        execute(config);
    }

    private static void execute(Config config) throws IOException, SQLException {
        FileType fileType = config.getInputFileFileType();

        Connection connection = null;
        try {
            String dbName = "DB_" + UUID.randomUUID().toString().replaceAll("-", "");
            DriverManager.registerDriver(new jdbcDriver());
            connection = DriverManager.getConnection("jdbc:hsqldb:file:/tmp/" + dbName + "/tmpdb;shutdown=true", "SA", "");

            String inputFilePath = config.getInputFile();

            String charset = CharsetUtil.getCharset(inputFilePath);

            String tableName = TABLE_NAME;
            boolean hasHeader = config.hasHeader();

            DBColumnList columnNames = CsvUtil.getColumnNames(inputFilePath, fileType, hasHeader, charset);
            CsvUtil.getColumnsType(inputFilePath, fileType, hasHeader, charset, columnNames);

            DBUtil.createTable(connection, tableName, columnNames);
            DBUtil.insert(connection, tableName, columnNames, fileType, hasHeader, inputFilePath, charset);
            DBUtil.showResult(connection, config.getSqlFilePath());
        } finally {
            CloseUtil.close(connection);
        }
    }
}
