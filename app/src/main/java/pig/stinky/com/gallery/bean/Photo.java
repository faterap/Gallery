package pig.stinky.com.gallery.bean;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Photo implements Serializable {

    private File mImage;
    private String mAlbumName;

    public Photo(File image, String albumName) {
        mImage = image;
        mAlbumName = albumName;
    }

    public void addPersonTag(PersonTag personTag) {
        // TODO: 10/12/18 better implementation
        personTag.setPhotoPath(getFullPath());
    }

    public void removePersonTag(PersonTag personTag) {
        // TODO: 10/12/18 better implementation
        personTag.setPhotoPath("");
    }

    public void addLocationTag(LocationTag locationTag) {
        // TODO: 10/12/18 better implementation
        locationTag.setPhotoPath(getFullPath());
    }

    public void removeLocationTag(LocationTag locationTag) {
        // TODO: 10/12/18 better implementation
        locationTag.setPhotoPath("");
    }

    public boolean exist() {
        return mImage != null && mImage.exists();
    }

    public String getName() {
        return mImage.getName();
    }

    public String getFullPath() {
        return mImage.getAbsolutePath();
    }

    public Bitmap getBitmap() {
        if (exist()) {
            return null;
        }

        return BitmapFactory.decodeFile(mImage.getAbsolutePath());
    }

    public String getAlbumName() {
        return mAlbumName;
    }
}
