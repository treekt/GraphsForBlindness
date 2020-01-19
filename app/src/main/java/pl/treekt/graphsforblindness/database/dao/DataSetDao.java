package pl.treekt.graphsforblindness.database.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import pl.treekt.graphsforblindness.database.entity.DataSet;

import java.util.ArrayList;

public class DataSetDao extends BaseDao<DataSet, Integer> {

    private static final String TABLE_NAME = "data_sets";

    private static final String _ID = "id";
    private static final String _TITLE = "title";
    private static final String _PREFIX = "prefix";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            _TITLE + " TEXT, " +
            _PREFIX + " TEXT" +
            ")";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public Integer save(DataSet dataType) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_TITLE, dataType.getTitle());
        contentValues.put(_PREFIX, dataType.getPrefix());
        return (int) writableDatabase().insert(TABLE_NAME, null, contentValues);
    }

    @Override
    @SuppressLint("Recycle")
    public DataSet read(Integer id) {
        Cursor cursor = readableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + _ID + " = " + id, null);
        DataSet dataType = null;
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            dataType = new DataSet();
            dataType.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
            dataType.setTitle(cursor.getString(cursor.getColumnIndex(_TITLE)));
            dataType.setPrefix(cursor.getString(cursor.getColumnIndex(_PREFIX)));
        }
        return dataType;
    }

    @SuppressLint("Recycle")
    public ArrayList<DataSet> readAll() {
        Cursor cursor = readableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<DataSet> dataTypes = new ArrayList<>();
        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                DataSet dataType = new DataSet();
                dataType.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
                dataType.setTitle(cursor.getString(cursor.getColumnIndex(_TITLE)));
                dataType.setPrefix(cursor.getString(cursor.getColumnIndex(_PREFIX)));

                dataTypes.add(dataType);
                cursor.moveToNext();
            }
        }

        return dataTypes;
    }

    @Override
    public void delete(Integer id) {
        writableDatabase().delete(TABLE_NAME, _ID + "=" + id, null);
    }

    @Override
    public boolean update(DataSet dataType) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_TITLE, dataType.getTitle());
        contentValues.put(_PREFIX, dataType.getPrefix());
        return writableDatabase().update(TABLE_NAME, contentValues, _ID + "=" + dataType.getId(), null) > 0;
    }

    public static String getCreateTableCommand() {
        return CREATE_TABLE;
    }

    public static String getDropTableCommand() {
        return DROP_TABLE;
    }
}
