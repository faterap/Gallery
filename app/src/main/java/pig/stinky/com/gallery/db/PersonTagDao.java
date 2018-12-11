package pig.stinky.com.gallery.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import pig.stinky.com.gallery.bean.PersonTag;
import pig.stinky.com.gallery.bean.Photo;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonTagDao {

    private PersonTagDao() {

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

    public static void addTag(PersonTag tag) {
        String sql = "INSERT INTO `photos`.`persontag` (`photoid`,`value`)  VALUES ('"
                + tag.getPhotoPath()
                + "','"
                + tag.getValue()
                + "')";
        runRawQuery(Collections.singletonList(sql));
    }

    public static void deleteTag(PersonTag tag) {
        String sql = "DELETE FROM `photos`.`persontag` WHERE `photoid` ='"
                + tag.getPhotoPath()
                + "' and `value` = '"
                + tag.getValue()
                + "'";
        runRawQuery(Collections.singletonList(sql));
    }

    public static Set<PersonTag> getPhotoTags(Photo photo) {
        Set<PersonTag> ret = new HashSet<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = DatabaseManager.getInstance().openDatabase();
            db.beginTransaction();

            String sql = "SELECT * FROM `photos`.`persontag` WHERE `photoid` = '"
                    + photo.getFullPath()
                    + "'";
            cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                PersonTag tag = new PersonTag(cursor.getString(2), cursor.getString(1));
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

    public static Set<PersonTag> getAllTags() {
        Set<PersonTag> ret = new HashSet<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = DatabaseManager.getInstance().openDatabase();
            db.beginTransaction();

            String sql = "SELECT * FROM `photos`.`persontag`";
            cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                PersonTag tag = new PersonTag(cursor.getString(2), cursor.getString(1));
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
