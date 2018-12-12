package pig.stinky.com.gallery.basic.move;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.Nullable;
import pig.stinky.com.gallery.BaseAdapter;
import pig.stinky.com.gallery.task.LoadTask;
import pig.stinky.com.gallery.R;
import pig.stinky.com.gallery.basic.RVActivity;
import pig.stinky.com.gallery.bean.Album;
import pig.stinky.com.gallery.bean.Photo;
import pig.stinky.com.gallery.db.AlbumDao;
import pig.stinky.com.gallery.db.PhotoDao;

import java.lang.ref.WeakReference;
import java.util.List;

public class MovePhotoActivity extends RVActivity {

    public static final String EXTRA_MOVE_PHOTO = "move_photo";

    public static final int MOVE_PHOTO_REQUEST_CODE = 100;

    private MovePhotoAdapter mAdapter;

    private Photo mPhoto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        mPhoto = (Photo) getIntent().getSerializableExtra(EXTRA_MOVE_PHOTO);

        mAdapter = new MovePhotoAdapter(this);
        mAdapter.setOnPhotoMovedListener(dst -> {
            // TODO: 12/12/18 do it in worker thread
            PhotoDao.movePhoto(mPhoto, new Album(mPhoto.getAlbumName()), dst);
            setResult(RESULT_OK, new Intent());
            finish();

        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAlbum();
    }

    private void refreshAlbum() {
        AlbumTask task = new AlbumTask(new WeakReference<>(this), mAdapter);
        task.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_move, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.move_photo:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private static class AlbumTask extends LoadTask<Album> {
        public AlbumTask(WeakReference<Context> mContext, BaseAdapter<Album> mAdapter) {
            super(mContext, mAdapter);
        }

        @Override
        protected List<Album> doInBackground() {
            return AlbumDao.loadAlbum();
        }
    }
}
