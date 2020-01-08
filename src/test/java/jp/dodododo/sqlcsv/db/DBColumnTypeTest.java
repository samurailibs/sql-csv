package jp.dodododo.sqlcsv.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DBColumnTypeTest {

    @Test
    public void check() {
        DBColumnType columnType = new DBColumnType();
        columnType.check(200);

        assertEquals(DBColumnType.DB_INT_TYPE, columnType.getType());
    }
}
