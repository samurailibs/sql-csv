package jp.dodododo.sqlcsv.db;

import jp.dodododo.sqlcsv.util.DateUtil;

import java.math.BigDecimal;

public class DBColumnType {
    protected boolean maybeInt = true;
    protected boolean maybeDouble = true;
    protected boolean maybeDate = true;

    public void check(Object value) {
        String stringValue = value.toString();
        if (maybeInt) {
            try {
                Long.parseLong(stringValue);
            } catch (NumberFormatException e) {
                maybeInt = false;
            }
        }
        if ((!maybeInt) && maybeDouble) {
            try {
                new BigDecimal(stringValue);
            } catch (NumberFormatException e) {
                maybeDouble = false;
            }
        }
        if ((!maybeInt) && (!maybeDouble) && maybeDate) {
            try {
                DateUtil.convert(stringValue);
            } catch (RuntimeException e) {
                maybeDate = false;
            }
        }
    }

    public String getType() {
        if (maybeInt) {
            return DB_INT_TYPE;
        }
        if (maybeDouble) {
            return DB_DOUBLE_TYPE;
        }
        if (maybeDate) {
            return DB_TIMESTAMP_TYPE;
        }
        return DB_STRING_TYPE;
    }

    public static final String DB_INT_TYPE = "BIGINT";
    public static final String DB_DOUBLE_TYPE = "DOUBLE";
    public static final String DB_TIMESTAMP_TYPE = "TIMESTAMP";
    public static final String DB_STRING_TYPE = "LONGVARCHAR";
    public static final String DB_BOOLEAN_TYPE = "BOOLEAN";

    public Object convert(String value) {
        if (maybeInt) {
            return value;
        }
        if (maybeDouble) {
            return value;
        }
        if (maybeDate) {
            return DateUtil.convert(value);
        }
        return value;
    }

    public boolean isString() {
        return DB_STRING_TYPE.equals(getType());
    }

    @Override
    public String toString() {
        return getType();
    }

    public static String toDbType(String s) {
        if (s.toUpperCase().startsWith("N")) {
            return DB_DOUBLE_TYPE;
        }
        if (s.toUpperCase().startsWith("B")) {
            return DB_BOOLEAN_TYPE;
        }
        if (s.toUpperCase().startsWith("S")) {
            return DB_STRING_TYPE;
        }
        if (s.toUpperCase().startsWith("D")) {
            return DB_TIMESTAMP_TYPE;
        }
        throw new IllegalArgumentException("Unknown type : " + s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DBColumnType that = (DBColumnType) o;

        if (maybeInt != that.maybeInt) return false;
        if (maybeDouble != that.maybeDouble) return false;
        return maybeDate == that.maybeDate;
    }

    @Override
    public int hashCode() {
        int result = (maybeInt ? 1 : 0);
        result = result + (maybeDouble ? 2 : 0);
        result = result + (maybeDate ? 4 : 0);
        return result;
    }
}
