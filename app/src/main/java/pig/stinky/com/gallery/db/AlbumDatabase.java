package pig.stinky.com.gallery.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlbumDatabase extends SQLiteOpenHelper {
    // Database info
    public static final String DB_NAME = "file_manager_db";
    public static final int DB_VERSION = 2;

    public AlbumDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}