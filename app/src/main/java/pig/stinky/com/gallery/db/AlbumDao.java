package pig.stinky.com.gallery.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import pig.stinky.com.gallery.bean.Album;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlbumDao {

    private static void runRawQuery(List<String> sqls) {
        SQLiteDatabase db = null;

        try {
            db = DatabaseManager.getInstance().openDatabase();
            db.beginTransaction();

            for (String sql : sqls) {
                db.rawQuery(sql, null);
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

    public static void createAlbum(Album album) {
        String sql = "INSERT INTO `photos`.`album` (`albumname`)  VALUES ('"
                + album.getAlbumName()
                + "')";
        runRawQuery(Collections.singletonList(sql));
    }

    public static void deleteAlbum(Album album) {
        String sql = "DELETE FROM `photos`.`album` WHERE (`albumname`) ='"
                + album.getAlbumName();
        runRawQuery(Collections.singletonList(sql));
    }

    public static void renameAlbum(Album album, String name) {
        String sql = "UPDATE `photos`.`album` SET `albumname` = '"
                + name + "' WHERE `albumname` = '"
                + album.getAlbumName();
        runRawQuery(Collections.singletonList(sql));
    }

    public static List<Album> loadAlbum() {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        List<Album> ret = new ArrayList<>();

        try {
            db = DatabaseManager.getInstance().openDatabase();
            db.beginTransaction();

            String sql = "SELECT * FROM `photos`.`album`";
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                Album album = new Album(cursor.getString(1));
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
        }
        return ret;
    }
}
