package pig.stinky.com.gallery.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GalleryDatabase extends SQLiteOpenHelper {







    // Database info
    public static final String DB_NAME = "photos";
    public static final int DB_VERSION = 2;

    public GalleryDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createPhotoSql = "CREATE TABLE `photo` (" +
                "  `albumid` varchar(45) NOT NULL," +
                "  `filepath` varchar(200) NOT NULL,"  +
                "  PRIMARY KEY (`albumid`,`filepath`)," +
//                "  KEY `filepath` (`filepath`)," +
                "  CONSTRAINT `albumid` FOREIGN KEY (`albumid`) REFERENCES `album` (`albumname`) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")";
        sqLiteDatabase.execSQL(createPhotoSql);

        String createAlbumSql = "CREATE TABLE `album` (" +
                "  `albumname` varchar(200) NOT NULL," +
                "  PRIMARY KEY (`albumname`)" +
                ")";
        sqLiteDatabase.execSQL(createAlbumSql);

        String createPersonTagSql = "CREATE TABLE `persontag` (" +
                "  `photoid` varchar(200) NOT NULL," +
                "  `value` varchar(45) NOT NULL," +
                "  PRIMARY KEY (`photoid`,`value`)," +
//                "  KEY `photoid` (`photoid`)," +
                "  CONSTRAINT `photo_id_1` FOREIGN KEY (`photoid`) REFERENCES `photo` (`filepath`) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")";
        /*
        String createPersonTagSql = "CREATE TABLE `persontag` (" +
            "  `photoid` varchar(200) NOT NULL," +
            "  `value` varchar(45) NOT NULL," +
            "  `albumsource` varchar(45) NOT NULL," +
            "  PRIMARY KEY (`photoid`,`value`)," +
            "  CONSTRAINT `photo_id_1` FOREIGN KEY (`photoid`) REFERENCES `photo` (`filepath`) ON DELETE CASCADE ON UPDATE CASCADE" +
            ")";
            */
        sqLiteDatabase.execSQL(createPersonTagSql);

        String createLocationTagSql = "CREATE TABLE `locationtag` (" +
                "  `photoname` varchar(45) NOT NULL," +
                "  `value` varchar(45) NOT NULL," +
                "  PRIMARY KEY (`photoname`,`value`)," +
                "  CONSTRAINT `photoname_1` FOREIGN KEY (`photoname`) REFERENCES `photo` (`filepath`) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")";
        /*

         String createLocationTagSql = "CREATE TABLE `locationtag` (" +
            "  `photoname` varchar(45) NOT NULL," +
            "  `value` varchar(45) NOT NULL," +
            "  `albumorigin` varchar(45) NOT NULL," +
            "  PRIMARY KEY (`photoname`,`value`)," +
            "  CONSTRAINT `photoname_1` FOREIGN KEY (`photoname`) REFERENCES `photo` (`filepath`) ON DELETE CASCADE ON UPDATE CASCADE" +
            ")";
     */
        sqLiteDatabase.execSQL(createLocationTagSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}