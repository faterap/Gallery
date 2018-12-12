package pig.stinky.com.gallery.detail;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import pig.stinky.com.gallery.R;
import pig.stinky.com.gallery.bean.Photo;
import pig.stinky.com.gallery.task.LocationTagTask;
import pig.stinky.com.gallery.task.PersonTagTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class PhotoDetailPagerAdapter extends PagerAdapter {

    private Context mContext;

    private List<Photo> mPhotos = new ArrayList<>();

    public PhotoDetailPagerAdapter(Context context, List<Photo> photos) {
        mContext = context;
        mPhotos.addAll(photos);
    }

    @Override
    public int getCount() {
        return mPhotos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Photo photo = mPhotos.get(position);

        View root = LayoutInflater.from(mContext).inflate(R.layout.photo_detail_pager_item, container, false);
        ImageView iv = root.findViewById(R.id.iv_photo);

        RecyclerView personRv = root.findViewById(R.id.rv_person_tags);
        RecyclerView locationRv = root.findViewById(R.id.rv_location_tags);

        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        personRv.setLayoutManager(manager);
        locationRv.setLayoutManager(manager);

        PersonTagAdapter personAdapter = new PersonTagAdapter(mContext);
        LocationTagAdapter locationAdapter = new LocationTagAdapter(mContext);
        personRv.setAdapter(personAdapter);
        locationRv.setAdapter(locationAdapter);

        iv.setImageBitmap(BitmapFactory.decodeFile(photo.getFullPath()));

        // load tag in AsyncTask
        PersonTagTask personTagTask = new PersonTagTask(new WeakReference<>(mContext), personAdapter, photo);
        LocationTagTask locationTagTask = new LocationTagTask(new WeakReference<>(mContext), locationAdapter, photo);
        personTagTask.execute();
        locationTagTask.execute();

//        personAdapter.setData(photo.getPersonTags());
//        locationAdapter.setData(photo.getLocationTags());

        container.addView(root);

        return root;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
    }
}
