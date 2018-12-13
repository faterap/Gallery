package pig.stinky.com.gallery.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import pig.stinky.com.gallery.bean.Album;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlbumDao {

    public static final int INDEX_ALBUM_NAME = 0;

    private static void runRawSql(List<String> sqls) {
        SQLiteDatabase db = null;

        try {
            db = DatabaseManager.getInstance().openDatabase();
            db.beginTransaction();

            for (String sql : sqls) {
                db.execSQL(sql);
            }

            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.endTransaction();
                DatabaseManager.getInstance().closeDatabase();
            }
        }
    }

    public static void addAlbum(Album album) {
        String sql = "INSERT INTO `album` (`albumname`) VALUES ('"
                + album.getAlbumName()
                + "')";
        runRawSql(Collections.singletonList(sql));
    }

    public static void deleteAlbum(Album album) {
        String sql = "DELETE FROM `album` WHERE (`albumname`) ='"
                + album.getAlbumName() + "'";
        runRawSql(Collections.singletonList(sql));
    }

    public static void renameAlbum(Album album, String name) {
        String sql = "UPDATE `album` SET `albumname` = '"
                + name + "' WHERE `albumname` = '"
                + album.getAlbumName() + "'";
        runRawSql(Collections.singletonList(sql));
    }

    public static List<Album> loadAlbum() {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        List<Album> ret = new ArrayList<>();

        try {
            db = DatabaseManager.getInstance().openDatabase();
            db.beginTransaction();

            String sql = "SELECT * FROM `album`";
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                Album album = new Album(cursor.getString(INDEX_ALBUM_NAME));
                ret.add(album);
            }

            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.endTransaction();
                DatabaseManager.getInstance().closeDatabase();
            }

            if (cursor != null) {
                cursor.close();
            }
        }
        return ret;
    }
}
