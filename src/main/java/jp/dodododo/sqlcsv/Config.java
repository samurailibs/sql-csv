package jp.dodododo.sqlcsv;

import jp.dodododo.sqlcsv.exception.ArgumentException;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class Config {
    protected String inputFile;
    protected FileType inputFileFileType;
    protected Boolean hasHeader;
    protected String sqlFilePath;

    public Config(Map<String, Object> config) throws ArgumentException {
        this.inputFile = (String) config.get("inputFile");
        this.inputFileFileType = FileType.toType((String) config.get("inputFileType"));
        Boolean inputFileHasHeader = (Boolean) config.get("inputFileHasHeader");
        this.hasHeader = inputFileHasHeader != null ? inputFileHasHeader : false;
        this.sqlFilePath = (String) config.get("sql");
    }

    public Config(List<String> argsList) throws ArgumentException {
        // ファイル形式(csv, tsv, ltsv)
        // -c
        // -t
        // -l
        // ヘッダーがある場合
        // --header
        // -h
        //
        // 入力ファイル
        // SQLファイル

        if (argsList.size() < 3) {
            throw new ArgumentException("<fileType> [--header] <inputFile> <sqlFile> を指定してください."); // TODO into resource
        }

        if (argsList.contains("-c")) {
            this.inputFileFileType = FileType.CSV;
        } else if (argsList.contains("-t")) {
            this.inputFileFileType = FileType.TSV;
        } else if (argsList.contains("-l")) {
            this.inputFileFileType = FileType.LTSV;
        } else {
            throw new ArgumentException("ファイルタイプを指定してください. -c:CSV, -t:TSV, -l:LTSV"); // TODO into resource
        }

        if (inputFileFileType == FileType.LTSV) {
            this.hasHeader = false;
        } else if (argsList.contains("-h") || argsList.contains("--header")) {
            this.hasHeader = true;
        }

        this.inputFile = argsList.get(argsList.size() - 2);
        this.sqlFilePath = argsList.get(argsList.size() - 1);
    }

    public static Config loadConfig(String path) throws FileNotFoundException, UnsupportedEncodingException, ArgumentException {
        Yaml y = new Yaml();
        @SuppressWarnings("unchecked")
        Map<String, Object> yaml = (Map<String, Object>) y.load(new InputStreamReader(new FileInputStream(path), "UTF-8"));
        return new Config(yaml);
    }

    public String getInputFile() {
        return inputFile;
    }

    public FileType getInputFileFileType() {
        return inputFileFileType;
    }

    public boolean hasHeader() {
        return hasHeader;
    }

    public String getSqlFilePath() {
        return sqlFilePath;
    }
}
