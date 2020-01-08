package jp.dodododo.sqlcsv.util;

import jp.dodododo.sqlcsv.FileType;
import jp.dodododo.sqlcsv.db.DBColumnList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CsvUtilTest {

    @Test
    public void getColumnNames_csv_header() throws IOException {
        DBColumnList columnNames = CsvUtil.getColumnNames("src/test/resources/csv/test.csv", FileType.CSV, true, "UTF8");
        assertTrue(columnNames.containsName("a"));
        assertTrue(columnNames.containsName("b"));
        assertTrue(columnNames.containsName("c"));
        assertTrue(columnNames.containsName("d"));
        assertTrue(columnNames.containsName("e"));
        assertFalse(columnNames.containsName("f"));
    }

    @Test
    public void getColumnNames_csv_no_header() throws IOException {
        DBColumnList columnNames = CsvUtil.getColumnNames("src/test/resources/csv/test_no_header.csv", FileType.CSV, false, "UTF8");
        assertTrue(columnNames.containsName("COL0"));
        assertTrue(columnNames.containsName("COL1"));
        assertTrue(columnNames.containsName("COL2"));
        assertTrue(columnNames.containsName("COL3"));
        assertTrue(columnNames.containsName("COL4"));
        assertFalse(columnNames.containsName("COL5"));
    }

    @Test
    public void getColumnNames_tsv_header() throws IOException {
        DBColumnList columnNames = CsvUtil.getColumnNames("src/test/resources/tsv/test.csv", FileType.TSV, true, "UTF8");
        assertTrue(columnNames.containsName("a"));
        assertTrue(columnNames.containsName("b"));
        assertTrue(columnNames.containsName("c"));
        assertTrue(columnNames.containsName("d"));
        assertTrue(columnNames.containsName("e"));
        assertFalse(columnNames.containsName("f"));
    }

    @Test
    public void getColumnNames_tsv_no_header() throws IOException {
        DBColumnList columnNames = CsvUtil.getColumnNames("src/test/resources/tsv/test_no_header.csv", FileType.TSV, false, "UTF8");
        assertTrue(columnNames.containsName("COL0"));
        assertTrue(columnNames.containsName("COL1"));
        assertTrue(columnNames.containsName("COL2"));
        assertTrue(columnNames.containsName("COL3"));
        assertTrue(columnNames.containsName("COL4"));
        assertFalse(columnNames.containsName("COL5"));
    }

    @Test
    public void getColumnNames_ltsv() throws IOException {
        DBColumnList columnNames = CsvUtil.getColumnNames("src/test/resources/ltsv/test.ltsv", FileType.LTSV, true, "UTF8");
        assertTrue(columnNames.containsName("a"));
        assertTrue(columnNames.containsName("b"));
        assertTrue(columnNames.containsName("c"));
        assertTrue(columnNames.containsName("d"));
        assertTrue(columnNames.containsName("e"));
        assertFalse(columnNames.containsName("f"));
    }

    @Test
    public void getColumnsType_csv_header() throws IOException {
        DBColumnList columnNames = CsvUtil.getColumnNames("src/test/resources/csv/test.csv", FileType.CSV, true, "UTF8");
        CsvUtil.getColumnsType("src/test/resources/csv/test.csv",FileType.CSV,true, "UTF8",columnNames);
        assertEquals("BIGINT", columnNames.get("a").getType().getType());
        assertEquals("DOUBLE", columnNames.get("b").getType().getType());
        assertEquals("LONGVARCHAR", columnNames.get("c").getType().getType());
        assertEquals("LONGVARCHAR", columnNames.get("d").getType().getType());
        assertEquals("TIMESTAMP", columnNames.get("e").getType().getType());
    }

    @Test
    public void getColumnsType_tsv_header() throws IOException {
        DBColumnList columnNames = CsvUtil.getColumnNames("src/test/resources/tsv/test.csv", FileType.TSV, true, "UTF8");
        CsvUtil.getColumnsType("src/test/resources/tsv/test.csv",FileType.TSV,true, "UTF8",columnNames);
        assertEquals("BIGINT", columnNames.get("a").getType().getType());
        assertEquals("DOUBLE", columnNames.get("b").getType().getType());
        assertEquals("LONGVARCHAR", columnNames.get("c").getType().getType());
        assertEquals("LONGVARCHAR", columnNames.get("d").getType().getType());
        assertEquals("TIMESTAMP", columnNames.get("e").getType().getType());
    }

    @Test
    public void getColumnsType_ltsv() throws IOException {
        DBColumnList columnNames = CsvUtil.getColumnNames("src/test/resources/ltsv/test.ltsv", FileType.LTSV, true, "UTF8");
        CsvUtil.getColumnsType("src/test/resources/ltsv/test.ltsv",FileType.LTSV,true, "UTF8",columnNames);
        assertEquals("BIGINT", columnNames.get("a").getType().getType());
        assertEquals("DOUBLE", columnNames.get("b").getType().getType());
        assertEquals("LONGVARCHAR", columnNames.get("c").getType().getType());
        assertEquals("LONGVARCHAR", columnNames.get("d").getType().getType());
        assertEquals("TIMESTAMP", columnNames.get("e").getType().getType());
    }
}
