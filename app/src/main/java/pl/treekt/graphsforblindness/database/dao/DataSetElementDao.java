package pl.treekt.graphsforblindness.database.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import pl.treekt.graphsforblindness.database.entity.DataSetElement;

import java.util.ArrayList;

public class DataSetElementDao extends BaseDao<DataSetElement, Integer> {

    private static final String TABLE_NAME = "data_set_elements";

    private static final String _ID = "id";
    private static final String _TITLE = "title";
    private static final String _VALUE = "value";
    private static final String _DATA_SET_ID = "data_set_id";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            _TITLE + " TEXT, " +
            _VALUE + " INT UNSIGNED, " +
            _DATA_SET_ID + " INTEGER, " +
            "CONSTRAINT fk_data_type FOREIGN KEY (" + _DATA_SET_ID + ") REFERENCES data_type(id)" +
            ")";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public Integer save(DataSetElement dataChart) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_TITLE, dataChart.getTitle());
        contentValues.put(_VALUE, dataChart.getValue());
        contentValues.put(_DATA_SET_ID, dataChart.getDataTypeId());
        return (int) writableDatabase().insert(TABLE_NAME, null, contentValues);
    }

    @Override
    @SuppressLint("Recycle")
    public DataSetElement read(Integer id) {
        Cursor cursor = readableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + _ID + " = " + id, null);
        DataSetElement dataChart = null;
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            dataChart = new DataSetElement();
            dataChart.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
            dataChart.setTitle(cursor.getString(cursor.getColumnIndex(_TITLE)));
            dataChart.setValue(cursor.getInt(cursor.getColumnIndex(_VALUE)));
            dataChart.setDataTypeId(cursor.getInt(cursor.getColumnIndex(_DATA_SET_ID)));

        }
        return dataChart;
    }

    @SuppressLint("Recycle")
    public ArrayList<DataSetElement> readAllByDataSetId(Integer dataSetId) {
        Cursor cursor = readableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + _DATA_SET_ID + " = " + dataSetId, null);
        ArrayList<DataSetElement> dataCharts = new ArrayList<>();
        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                DataSetElement dataChart = new DataSetElement();
                dataChart.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
                dataChart.setTitle(cursor.getString(cursor.getColumnIndex(_TITLE)));
                dataChart.setValue(cursor.getInt(cursor.getColumnIndex(_VALUE)));
                dataChart.setDataTypeId(cursor.getInt(cursor.getColumnIndex(_DATA_SET_ID)));

                dataCharts.add(dataChart);
                cursor.moveToNext();
            }
        }

        return dataCharts;
    }

    @Override
    public void delete(Integer id) {
        writableDatabase().delete(TABLE_NAME, _ID + "=" + id, null);
    }

    public void deleteAllByDataSetId(Integer dataSetId) {
        writableDatabase().delete(TABLE_NAME, _DATA_SET_ID + "=" + dataSetId, null);
    }

    @Override
    public boolean update(DataSetElement dataChart) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_TITLE, dataChart.getTitle());
        contentValues.put(_VALUE, dataChart.getValue());
        contentValues.put(_DATA_SET_ID, dataChart.getDataTypeId());
        return writableDatabase().update(TABLE_NAME, contentValues, _ID + "=" + dataChart.getId(), null) > 0;
    }

    public static String getCreateTableCommand() {
        return CREATE_TABLE;
    }

    public static String getDropTableCommand() {
        return DROP_TABLE;
    }
}
