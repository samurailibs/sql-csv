package jp.dodododo.sqlcsv.util;

import jp.dodododo.sqlcsv.FileType;
import jp.dodododo.sqlcsv.db.DBColumn;
import jp.dodododo.sqlcsv.db.DBColumnList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class CsvUtil {
    public static CSVParser createCSVParser(InputStream inputStream, char delimiter, String charset) throws IOException {
        Reader fileReader = new BufferedReader(new InputStreamReader(inputStream, charset));
        return new CSVParser(new BufferedReader(fileReader), CSVFormat.newFormat(delimiter));
    }

    public static DBColumnList getColumnNames(String inputFilePath, FileType fileType, boolean hasHeader, String charset) throws IOException {
        InputStream inputStream = null;
        CSVParser csvParser = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(new File(inputFilePath)));
            csvParser = CsvUtil.createCSVParser(inputStream, fileType.getChar(), charset);
            Iterator<CSVRecord> iterator = csvParser.iterator();
            Set<DBColumn> columnNames = new LinkedHashSet<DBColumn>();
            if (fileType.equals(FileType.CSV) || fileType.equals(FileType.TSV)) {
                CSVRecord firstRow = iterator.next();
                for (int i = 0; i < firstRow.size(); i++) {
                    if (hasHeader) {
                        columnNames.add(new DBColumn(firstRow.get(i)));
                    } else {
                        columnNames.add(new DBColumn("COL" + i));
                    }
                }
            } else if (fileType.equals(FileType.LTSV)) {
                while (iterator.hasNext()) {
                    CSVRecord row = iterator.next();
                    int rowSize = row.size();
                    for (int i = 0; i < rowSize; i++) {
                        String columnName = LtsvUtil.getKey(row.get(i));
                        if (!columnNames.contains(columnName) && DBUtil.validColumnName(columnName)) {
                            columnNames.add(new DBColumn(columnName));
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException("fileType : " + fileType);
            }
            return new DBColumnList(columnNames);
        } finally {
            CloseUtil.close(csvParser);
            CloseUtil.close(inputStream);
        }
    }

    public static void getColumnsType(String inputFilePath, FileType fileType, boolean hasHeader, String charset, DBColumnList dbColumns) throws IOException {
        InputStream inputStream = null;
        CSVParser csvParser = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(new File(inputFilePath)));
            csvParser = CsvUtil.createCSVParser(inputStream, fileType.getChar(), charset);
            Iterator<CSVRecord> iterator = csvParser.iterator();
            if (fileType != FileType.LTSV && hasHeader) {
                iterator.next();
            }

            while (iterator.hasNext()) {
                CSVRecord csvRecord = iterator.next();
                int size = csvRecord.size();
                for (int i = 0; i < size; i++) {
                    DBColumn dbColumn;
                    if (fileType == FileType.LTSV) {
                        dbColumn = new DBColumn(LtsvUtil.getKey(csvRecord.get(i)));
                        if (!dbColumns.containsName(dbColumn)) {
                            continue;
                        } else {
                            dbColumn = dbColumns.get(dbColumn);
                        }
                    } else {
                        dbColumn = dbColumns.get(i);
                    }
                    dbColumn.getType().check(fileType.getStringValue(csvRecord.get(i)));
                }
            }
        } finally {
            CloseUtil.close(csvParser);
            CloseUtil.close(inputStream);
        }
    }
}
