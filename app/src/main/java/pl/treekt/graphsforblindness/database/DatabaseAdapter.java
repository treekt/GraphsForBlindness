package pl.treekt.graphsforblindness.database;

import android.content.Context;
import pl.treekt.graphsforblindness.exception.ResourceNotInitializedException;

public class DatabaseAdapter {

    private static DatabaseHelper databaseHelper;

    public static DatabaseHelper initializeInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }

        return databaseHelper;
    }

    public static DatabaseHelper getInstance() {
        if(databaseHelper == null) {
            throw new ResourceNotInitializedException("database adapter");
        }
        return databaseHelper;
    }
}
