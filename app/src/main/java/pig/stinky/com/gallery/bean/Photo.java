package pig.stinky.com.gallery.bean;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Photo implements Serializable {

    private File mImage;

    private Set<PersonTag> mPersonTags = new HashSet<>();
    private Set<LocationTag> mLocationTags = new HashSet<>();

    public Photo(File image) {
        mImage = image;
    }

    public void addPersonTag(PersonTag personTag) {
        mPersonTags.add(personTag);
        personTag.add(this);
    }

    public void removePersonTag(PersonTag personTag) {
        mPersonTags.remove(personTag);
        personTag.remove(this);
    }

    public void addLocationTag(LocationTag locationTag) {
        mLocationTags.add(locationTag);
        locationTag.add(this);
    }

    public void removeLocationTag(LocationTag locationTag) {
        mLocationTags.remove(locationTag);
        locationTag.remove(this);
    }

    public boolean exist() {
        return mImage != null && mImage.exists();
    }

    public String getName() {
        return mImage.getName();
    }

    public Bitmap getBitmap() {
        if (exist()) {
            return null;
        }

        return BitmapFactory.decodeFile(mImage.getAbsolutePath());
    }

}
