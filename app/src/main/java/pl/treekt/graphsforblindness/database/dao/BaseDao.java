package pl.treekt.graphsforblindness.database.dao;

import android.database.sqlite.SQLiteDatabase;
import pl.treekt.graphsforblindness.database.DatabaseAdapter;

public abstract class BaseDao<T, K> {

    private static SQLiteDatabase writableDatabase;
    private static SQLiteDatabase readableDatabase;

    public BaseDao() {
        writableDatabase = DatabaseAdapter.getInstance().getWritableDatabase();
        readableDatabase = DatabaseAdapter.getInstance().getReadableDatabase();
    }

    public abstract K save(T object);

    public abstract T read(K id);

    public abstract void delete(K id);

    public abstract boolean update(T object);

    protected SQLiteDatabase writableDatabase() {
        return writableDatabase;
    }

    protected SQLiteDatabase readableDatabase() {
        return readableDatabase;
    }

}
