package pig.stinky.com.gallery.db;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import pig.stinky.com.gallery.bean.Album;
import pig.stinky.com.gallery.bean.LocationTag;
import pig.stinky.com.gallery.bean.PersonTag;
import pig.stinky.com.gallery.bean.Photo;

import java.io.File;
import java.util.*;

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
                setPhotoTag(db, photo);
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

            if (cursor != null) {
                cursor.close();
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
                setPhotoTag(db, photo);
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

            if (cursor != null) {
                cursor.close();
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

    public static List<Photo> searchByPersonTag(String key) {
        List<Photo> ret = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = DatabaseManager.getInstance().openDatabase();
            db.beginTransaction();

            String sql = "SELECT p.albumid, t1.photoid FROM ( SELECT l.photoid FROM photos.persontag l WHERE value Like '"
                    + key
                    + "%')t1, photos.photo p WHERE p.filepath = t1.photoid GROUP BY t1.photoid";
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                Photo photo = new Photo(new File(cursor.getString(2)), cursor.getString(1));
                setPhotoTag(db, photo);
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

            if (cursor != null) {
                cursor.close();
            }
        }
        return ret;
    }

    public static List<Photo> searchByLocationTag(String key) {
        List<Photo> ret = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = DatabaseManager.getInstance().openDatabase();
            db.beginTransaction();

            String sql = "SELECT p.albumid, t1.photoname FROM ( SELECT l.photoname FROM photos.locationtag l WHERE value Like '"
                    + key
                    + "%')t1, photos.photo p WHERE p.filepath = t1.photoname GROUP BY t1.photoname";
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                Photo photo = new Photo(new File(cursor.getString(2)), cursor.getString(1));
                setPhotoTag(db, photo);
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

            if (cursor != null) {
                cursor.close();
            }
        }
        return ret;
    }

    public static List<Photo> searchByTags(String personKey, String locationKey) {
        List<Photo> ret = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = DatabaseManager.getInstance().openDatabase();
            db.beginTransaction();

            String sql = "SELECT photo.albumid, t1.photoid FROM (( SELECT p.photoid FROM photos.persontag p WHERE value Like '"
                    + personKey
                    + "%') UNION ALL (SELECT l.photoname FROM photos.locationtag l WHERE value Like '"
                    + locationKey
                    + "'))t1,photos.photo WHERE photo.filepath = t1.photoid GROUP BY t1.photoid";
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                Photo photo = new Photo(new File(cursor.getString(2)), cursor.getString(1));
                setPhotoTag(db, photo);
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

            if (cursor != null) {
                cursor.close();
            }
        }
        return ret;
    }

    private static void setPhotoTag(SQLiteDatabase db, Photo photo) {
        String photoPath = photo.getFullPath();
        Cursor personCursor = null;
        Cursor locationCursor = null;

        try {
            String personTagSql = "SELECT `value` FROM `photos`.`persontag` WHERE `photoid` = '"
                    + photoPath
                    + "'";

            personCursor = db.rawQuery(personTagSql, null);

            Set<PersonTag> personTags = new HashSet<>();
            while (personCursor.moveToNext()) {
                PersonTag personTag = new PersonTag(personCursor.getString(1), photoPath);
                personTags.add(personTag);
            }
            photo.setPersonTag(personTags);

            String locationTagSql = "SELECT `value` FROM `photos`.`locationtag` WHERE `photoname` = '"
                    + photoPath
                    + "'";

            locationCursor = db.rawQuery(locationTagSql, null);

            Set<LocationTag> locationTags = new HashSet<>();
            while (locationCursor.moveToNext()) {
                LocationTag locationTag = new LocationTag(locationCursor.getString(1), photoPath);
                locationTags.add(locationTag);
            }
            photo.setLocationTags(locationTags);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (personCursor != null) {
                personCursor.close();
            }

            if (locationCursor != null) {
                locationCursor.close();
            }
        }
    }

}
