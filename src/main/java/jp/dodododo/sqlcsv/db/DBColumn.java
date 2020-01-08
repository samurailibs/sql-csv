package jp.dodododo.sqlcsv.db;

public class DBColumn {
    protected String name;
    protected DBColumnType type = new DBColumnType();

    public DBColumn(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public DBColumnType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBColumn dbColumn = (DBColumn) o;
        return name.equals(dbColumn.name) && type.equals(dbColumn.type);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return type + " " + name;
    }
}
