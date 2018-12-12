package pig.stinky.com.gallery.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import pig.stinky.com.gallery.bean.LocationTag;
import pig.stinky.com.gallery.bean.Photo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocationTagDao {

    private LocationTagDao() {

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

    public static void addTag(LocationTag tag) {
        String sql = "INSERT INTO `photos`.`locationtag` (`photoname`,`value`)  VALUES ('"
                + tag.getPhotoPath()
                + "','"
                + tag.getValue()
                + "')";
        runRawQuery(Collections.singletonList(sql));
    }

    public static void deleteTag(LocationTag tag) {
        String sql = "DELETE FROM `photos`.`locationtag` WHERE `photoname` ='"
                + tag.getPhotoPath()
                + "' and `value` = '"
                + tag.getValue()
                + "'";
        runRawQuery(Collections.singletonList(sql));
    }

    public static List<LocationTag> getPhotoLags(Photo photo) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<LocationTag> ret = new ArrayList<>();

        try {
            db = DatabaseManager.getInstance().openDatabase();
            db.beginTransaction();

            String sql = "SELECT * FROM `photos`.`locationtag` WHERE `photoname` = '"
                    + photo.getFullPath()
                    + "'";
            cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                LocationTag tag = new LocationTag(cursor.getString(2), cursor.getString(1));
                ret.add(tag);
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

    public static List<LocationTag> getAllTags(Photo photo) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<LocationTag> ret = new ArrayList<>();

        try {
            db = DatabaseManager.getInstance().openDatabase();
            db.beginTransaction();

            String sql = "SELECT * FROM `photos`.`locationtag`";
            cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                LocationTag tag = new LocationTag(cursor.getString(2), cursor.getString(1));
                ret.add(tag);
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
