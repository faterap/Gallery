package pig.stinky.com.gallery.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import pig.stinky.com.gallery.bean.Album;
import pig.stinky.com.gallery.bean.Photo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhotoDao {

    private PhotoDao() {

    }

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

    public static List<Photo> loadPhotos(Album album) {
        List<Photo> ret = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = DatabaseManager.getInstance().openDatabase();
            db.beginTransaction();

            String sql = "SELECT * FROM `photos`.`photo` WHERE `albumid` = '"
                    + album.getAlbumName()
                    + "'";
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                Photo photo = new Photo(new File(cursor.getString(2)), cursor.getString(1));
                ret.add(photo);
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

    public static List<Photo> loadAllPhotos() {
        List<Photo> ret = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = DatabaseManager.getInstance().openDatabase();
            db.beginTransaction();

            String sql = "SELECT * FROM `photos`.`photo`;";
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                Photo photo = new Photo(new File(cursor.getString(2)), cursor.getString(1));
                ret.add(photo);
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

    public static void movePhoto(Photo photo, Album src, Album dst) {
        String insertSql = "INSERT INTO `photos`.`photo` (`filepath`,`albumid`)  VALUES ('"
                + photo.getFullPath()
                + "','"
                + dst.getAlbumName()
                + "')";

        String deleteSql = "DELETE FROM `photos`.`photo` WHERE `albumid` ='"
                + src.getAlbumName()
                + "' and `filepath` = '"
                + photo.getFullPath()
                + "'";
        List<String> sql = new ArrayList<>();
        sql.add(insertSql);
        sql.add(deleteSql);
        runRawQuery(sql);
    }

    public static void deletePhoto(Photo photo) {
        String sql = "DELETE FROM `photos`.`photo` WHERE `albumid` ='"
                + photo.getAlbumName()
                + "' and `filepath` = '"
                + photo.getFullPath()
                + "'";
        runRawQuery(Collections.singletonList(sql));
    }

    public static void addPhoto(Photo photo) {
        String sql = "INSERT INTO `photos`.`photo` (`filepath`,`albumid`)  VALUES ('"
                + photo.getFullPath()
                + "','"
                + photo.getAlbumName()
                + "')";
        runRawQuery(Collections.singletonList(sql));
    }

}
