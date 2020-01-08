package jp.dodododo.sqlcsv;

import jp.dodododo.sqlcsv.db.DBColumn;
import jp.dodododo.sqlcsv.db.DBColumnList;
import jp.dodododo.sqlcsv.util.LtsvUtil;

public enum FileType {
    CSV(','), TSV('\t'), LTSV('\t');

    char c;

    FileType(char c) {
        this.c = c;
    }

    public char getChar() {
        return c;
    }

    public Object getStringValue(String text) {
        if (this == CSV || this == TSV) {
            return text;
        } else if (this == LTSV) {
            return LtsvUtil.getValue(text);
        } else {
            throw new IllegalStateException();
        }
    }

    public Object getDBValue(String text, DBColumnList columns) {
        if (this == CSV || this == TSV) {
            return text;
        } else if (this == LTSV) {
            DBColumn column =  columns.get(LtsvUtil.getKey(text));
            return column.getType().convert(LtsvUtil.getValue(text));
        } else {
            throw new IllegalStateException();
        }
    }

    public static FileType toType(String inputFileType) {
        char c = inputFileType.charAt(0);
        switch (c) {
            case 'T':
            case 't':
                return FileType.TSV;
            case 'C':
            case 'c':
                return FileType.CSV;
            case 'L':
            case 'l':
                return FileType.LTSV;
            default:
                throw new IllegalStateException("inputFileType : " + inputFileType);
        }
    }
}
