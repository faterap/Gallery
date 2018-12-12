package pig.stinky.com.gallery.bean;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Photo implements Parcelable {

    private File mImage;
    private String mAlbumName;

    // TODO: 12/12/18 unused
    private List<PersonTag> mPersonTags = new ArrayList<>();
    private List<LocationTag> mLocationTags = new ArrayList<>();

    public Photo(File image, String albumName) {
        mImage = image;
        mAlbumName = albumName;
    }

    protected Photo(Parcel in) {
        mImage = (File) in.readSerializable();
        mAlbumName = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(mImage);
        dest.writeString(mAlbumName);
    }

    public List<PersonTag> getPersonTags() {
        return new ArrayList<>(mPersonTags);
    }

    public void setPersonTag(List<PersonTag> personTags) {
        mPersonTags.clear();
        mPersonTags.addAll(personTags);
    }

    public List<LocationTag> getLocationTags() {
        return new ArrayList<>(mLocationTags);
    }

    public void setLocationTags(List<LocationTag> locationTags) {
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
