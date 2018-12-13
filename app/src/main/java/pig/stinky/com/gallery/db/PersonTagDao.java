package pig.stinky.com.gallery.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import pig.stinky.com.gallery.bean.PersonTag;
import pig.stinky.com.gallery.bean.Photo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonTagDao {

    public static final int INDEX_TAG_PHOTO_PATH = 0;
    public static final int INDEX_TAG_NAME = 1;

    private PersonTagDao() {

    }

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

    public static void addTag(PersonTag tag) {
        String sql = "INSERT INTO `persontag` (`photoid`,`value`)  VALUES ('"
                + tag.getPhotoPath()
                + "','"
                + tag.getValue()
                + "')";
        runRawSql(Collections.singletonList(sql));
    }

    public static void deleteTag(PersonTag tag) {
        String sql = "DELETE FROM `persontag` WHERE `photoid` ='"
                + tag.getPhotoPath()
                + "' and `value` = '"
                + tag.getValue()
                + "'";
        runRawSql(Collections.singletonList(sql));
    }

    public static List<PersonTag> getPhotoTags(Photo photo) {
        List<PersonTag> ret = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = DatabaseManager.getInstance().openDatabase();
            db.beginTransaction();

            String sql = "SELECT * FROM `persontag` WHERE `photoid` = '"
                    + photo.getFullPath()
                    + "'";
            cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                PersonTag tag = new PersonTag(cursor.getString(INDEX_TAG_NAME), cursor.getString(INDEX_TAG_PHOTO_PATH));
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

    public static List<PersonTag> getAllTags() {
        List<PersonTag> ret = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = DatabaseManager.getInstance().openDatabase();
            db.beginTransaction();

            String sql = "SELECT * FROM `persontag`";
            cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                PersonTag tag = new PersonTag(cursor.getString(INDEX_TAG_NAME), cursor.getString(INDEX_TAG_PHOTO_PATH));
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
