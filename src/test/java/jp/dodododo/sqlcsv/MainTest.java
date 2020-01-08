package jp.dodododo.sqlcsv;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

public class MainTest {

    @Test
    public void main_csv() throws IOException, SQLException {
        Main.main("-c", "-h", "src/test/resources/csv/test.csv", "src/test/resources/test.sql");
    }

    @Test
    public void main_tsv() throws IOException, SQLException {
        Main.main("-t", "-h", "src/test/resources/tsv/test.csv", "src/test/resources/test.sql");
    }

    @Test
    public void main_ltsv() throws IOException, SQLException {
        Main.main("-l", "src/test/resources/ltsv/test.ltsv", "src/test/resources/test.sql");
    }
}
