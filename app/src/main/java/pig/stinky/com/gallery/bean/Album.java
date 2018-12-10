package pig.stinky.com.gallery.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Album implements Serializable {

    private String mAlbumName;

    public Album(String albumName) {
        mAlbumName = albumName;
    }

    public String getAlbumName() {
        return mAlbumName;
    }
}
