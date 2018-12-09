package pig.stinky.com.gallery.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Album implements Serializable {

    private String mAlbumName;
    private List<Photo> mPhotos = new ArrayList<>();

    public Album(String albumName) {
        mAlbumName = albumName;
    }

    public void addPhoto(Photo photo) {
        if (!mPhotos.contains(photo)) {
            mPhotos.add(photo);
        }
    }

    public void removePhoto(Photo photo) {
        mPhotos.remove(photo);
    }

    public String getAlbumName() {
        return mAlbumName;
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }
}
