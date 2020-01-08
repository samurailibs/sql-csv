package jp.dodododo.sqlcsv.util;

import jp.dodododo.sqlcsv.db.DBColumnType;
import jp.dodododo.sqlcsv.FileType;
import jp.dodododo.sqlcsv.db.DBColumn;
import jp.dodododo.sqlcsv.db.DBColumnList;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DBUtil {
    public static boolean validColumnName(String columnName) {
        int count = 0;
        for (int i = 0; i < columnName.length(); i++) {
            char c = columnName.charAt(i);
            switch (c) {
                case '@':
                case '{':
                case '}':
                case '"':
                    count++;
                    break;
            }
        }
        return count <= 2;
    }

    public static void createTable(Connection connection, String tableName, DBColumnList columns) throws SQLException {
        StringBuilder createTable = new StringBuilder(1024);
        createTable.append("CREATE TABLE ");
        createTable.append(tableName);
        createTable.append(" ( ");
        for (DBColumn column : columns) {
            createTable.append(column.getName().replaceAll("[.@{}\"]", "_"));
            createTable.append(" ");
            createTable.append(column.getType());
            createTable.append(",");
        }
        createTable.setLength(createTable.length() - 1);
        createTable.append(")");

        Statement st = null;
        try {
            st = connection.createStatement();
            st.executeUpdate(createTable.toString());
        } finally {
            CloseUtil.close(st);
        }
    }

    public static void insert(Connection connection, String tableName, DBColumnList columns, FileType fileType, boolean hasHeader, String inputFilePath, String charset) throws SQLException, IOException {
        InputStream inputStream = null;
        CSVParser csvParser = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(new File(inputFilePath)));
            csvParser = CsvUtil.createCSVParser(inputStream, fileType.getChar(), charset);
            Iterator<CSVRecord> iterator = csvParser.iterator();
            if (hasHeader) {
                iterator.next();
            }

            StringBuilder insertSQL = new StringBuilder(128);
            insertSQL.append("INSERT INTO ");
            insertSQL.append(tableName);
            insertSQL.append(" VALUES (");
            for (int i = 0; i < columns.size(); i++) {
                insertSQL.append("?,");
            }
            insertSQL.setLength(insertSQL.length() - 1);
            insertSQL.append(")");

            PreparedStatement ps = connection.prepareStatement(insertSQL.toString());
            while (iterator.hasNext()) {
                CSVRecord csvRecord = iterator.next();
                Iterator<String> values = csvRecord.iterator();
                if (fileType == FileType.LTSV) {
                    Map<String, String> columnValue = new HashMap<String, String>(csvRecord.size());
                    while (values.hasNext()) {
                        String text = values.next();
                        columnValue.put(LtsvUtil.getKey(text), LtsvUtil.getValue(text));
                    }
                    int i = 1;
                    for (DBColumn column : columns) {
                        String value = columnValue.get(column.getName());
                        DBColumnType dbColumnType = column.getType();
                        ps.setObject(i++, dbColumnType.convert(value));
                    }
                } else {
                    int i = 1;
                    while (values.hasNext()) {
                        Object value = fileType.getDBValue(values.next(), columns);
                        ps.setObject(i++, value);
                    }
                }
                ps.execute();
            }
            ps.close();
        } finally {
            CloseUtil.close(csvParser);
            CloseUtil.close(inputStream);
        }
    }

    public static void showResult(Connection connection, String sqlFilePath) throws SQLException, IOException {
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(TextUtil.readText(sqlFilePath));

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<String> columnNames = new ArrayList<String>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }
            while (rs.next()) {
                StringBuilder row = new StringBuilder(512);
                for (String columnName : columnNames) {
                    row.append(columnName);
                    row.append("=");
                    row.append(rs.getObject(columnName));
                    row.append(", ");
                }
                row.setLength(row.length() - 2);
                System.out.println(row.toString());
            }
            rs.close();
        } finally {
            CloseUtil.close(st);
        }
    }

}
