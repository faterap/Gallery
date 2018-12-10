package pig.stinky.com.gallery.bean;

import java.util.HashSet;
import java.util.Set;

public class PersonTag {

    private String mValue;

    // TODO: 10/12/18 1 image --- 1 path?
    private String mPhotoPath;

    public PersonTag(String value, String path) {
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
