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

    private Set<PersonTag> mPersonTags = new HashSet<>();
    private Set<LocationTag> mLocationTags = new HashSet<>();

    public Photo(File image, String albumName) {
        mImage = image;
        mAlbumName = albumName;
    }

    public Set<PersonTag> getPersonTags() {
        return new HashSet<>(mPersonTags);
    }
    public void setPersonTag(Set<PersonTag> personTags) {
        mPersonTags.clear();
        mPersonTags.addAll(personTags);
    }

    public Set<LocationTag> getLocationTags() {
        return new HashSet<>(mLocationTags);
    }
    public void setLocationTags(Set<LocationTag> locationTags) {
        mLocationTags.clear();
        mLocationTags.addAll(locationTags);
    }
    public void addPersonTag(PersonTag personTag) {
        mPersonTags.add(personTag);
        personTag.setPhotoPath(getFullPath());
    }
    public void removePersonTag(PersonTag personTag) {
        mPersonTags.remove(personTag);
        personTag.setPhotoPath("");
    }
    public void addLocationTag(LocationTag locationTag) {
        mLocationTags.add(locationTag);
        locationTag.setPhotoPath(getFullPath());
    }
    public void removeLocationTag(LocationTag locationTag) {
        mLocationTags.remove(locationTag);
        locationTag.setPhotoPath(getFullPath());
    }

//    public void addPersonTag(PersonTag personTag) {
//        // TODO: 10/12/18 better implementation
//        personTag.setPhotoPath(getFullPath());
//    }
//
//    public void removePersonTag(PersonTag personTag) {
//        // TODO: 10/12/18 better implementation
//        personTag.setPhotoPath("");
//    }
//
//    public void addLocationTag(LocationTag locationTag) {
//        // TODO: 10/12/18 better implementation
//        locationTag.setPhotoPath(getFullPath());
//    }
//
//    public void removeLocationTag(LocationTag locationTag) {
//        // TODO: 10/12/18 better implementation
//        locationTag.setPhotoPath("");
//    }

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
