package pl.treekt.graphsforblindness.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import pl.treekt.graphsforblindness.database.dao.DataSetElementDao;
import pl.treekt.graphsforblindness.database.dao.DataSetDao;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "graps_for_blindness";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DataSetDao.getCreateTableCommand());
        sqLiteDatabase.execSQL(DataSetElementDao.getCreateTableCommand());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DataSetElementDao.getDropTableCommand());
        sqLiteDatabase.execSQL(DataSetDao.getDropTableCommand());
        onCreate(sqLiteDatabase);
    }
}
