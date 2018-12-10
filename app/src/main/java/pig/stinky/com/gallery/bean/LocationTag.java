package pig.stinky.com.gallery.bean;

public class LocationTag {

    private String mValue;

    // TODO: 10/12/18 1 image --- 1 path?
    private String mPhotoPath;

    public LocationTag(String value, String path) {
        mValue = value;
        mPhotoPath = path;
    }

    public String getValue() {
        return mValue;
    }

    public String getPhotoPath() {
        return mPhotoPath;
    }

    public void setPhotoPath(String path) {
        this.mPhotoPath = path;
    }
}
