package pig.stinky.com.gallery.bean;

import java.util.HashSet;
import java.util.Set;

public class PersonTag {

    private String mName;
    private Set<Photo> mPhotos = new HashSet<>();

    public PersonTag(String name) {
        mName = name;
    }

    public void add(Photo photo) {
        mPhotos.add(photo);
    }

    public void remove(Photo photo) {
        mPhotos.remove(photo);
    }

}
