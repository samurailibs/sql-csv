package jp.dodododo.sqlcsv.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DBColumnList extends ArrayList<DBColumn> {
    protected Map<String, DBColumn> dbColumnMap = new HashMap<String, DBColumn>();

    public DBColumnList(Collection<DBColumn> collection) {
        super(collection);
        for (DBColumn c : collection) {
            dbColumnMap.put(c.getName(), c);
        }
    }

    public boolean containsName(DBColumn dbColumn) {
        return containsName(dbColumn.getName());
    }

    public DBColumn get(DBColumn dbColumn) {
        return dbColumnMap.get(dbColumn.getName());
    }

    public DBColumn get(String columnName) {
        return dbColumnMap.get(columnName);
    }

    @Override
    public boolean add(DBColumn dbColumn) {
        dbColumnMap.put(dbColumn.getName(), dbColumn);
        return super.add(dbColumn);
    }

    @Override
    public boolean addAll(Collection<? extends DBColumn> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends DBColumn> c) {
        throw new UnsupportedOperationException();
    }

    public boolean containsName(String columnName) {
        return dbColumnMap.containsKey(columnName);
    }
}
