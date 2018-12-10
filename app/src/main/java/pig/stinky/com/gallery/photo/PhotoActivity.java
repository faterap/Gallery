package pig.stinky.com.gallery.photo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pig.stinky.com.gallery.LoadTask;
import pig.stinky.com.gallery.db.AlbumDao;
import pig.stinky.com.gallery.BaseAdapter;
import pig.stinky.com.gallery.R;
import pig.stinky.com.gallery.album.AlbumActivity;
import pig.stinky.com.gallery.bean.Album;
import pig.stinky.com.gallery.bean.Photo;
import pig.stinky.com.gallery.db.PhotoDao;
import pig.stinky.com.gallery.detail.PhotoDetailActivity;
import pig.stinky.com.gallery.utils.DialogHelper;

import java.lang.ref.WeakReference;
import java.util.List;

public class PhotoActivity extends AppCompatActivity {

    public static final String EXTRA_OPEN_PHOTO = "open_photo";

    private RecyclerView mRecyclerView;
    private PhotoAdapter mAdapter;

    private Album mAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        mAlbum = (Album) getIntent().getSerializableExtra(AlbumActivity.EXTRA_OPEN_ALBUM);

        mRecyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager manager = new GridLayoutManager(this, 5);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new PhotoAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        if (mAlbum != null) {
            setTitle(mAlbum.getAlbumName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshPhotos();
    }

    private void refreshPhotos() {
        PhotoTask task = new PhotoTask(new WeakReference<Context>(this), mAdapter, mAlbum);
        task.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_photo:
                // TODO: 2018/12/10 add photo picker

                return true;
            case R.id.delete_album:
                AlertDialog deleteDialog = DialogHelper.buildDeleteDialog(this, "Delete this album?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2018/12/10 do it in thread
                        if (mAlbum != null) {
                            AlbumDao.deleteAlbum(mAlbum);
                        }

                        // exit activity after album deleted
                        finish();
                    }
                });
                deleteDialog.show();
                return true;
            case R.id.rename_album:
                View root = View.inflate(this, R.layout.dialog_rename, null);
                final EditText et = root.findViewById(R.id.rename);
                if (et != null) {
                    et.selectAll();
                    et.setText(mAlbum.getAlbumName());
                }

                AlertDialog renameDialog = DialogHelper.buildCustomViewDialog(this, "Rename Album", root, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2018/12/10 do it in thread
                        if (et != null) {
                            AlbumDao.renameAlbum(mAlbum, et.getText().toString());
                        }

                        refreshPhotos();
                    }
                });
                renameDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    static class PhotoTask extends LoadTask<Photo> {
        Album mAlbum;

        public PhotoTask(WeakReference<Context> mContext, BaseAdapter<Photo> mAdapter, Album album) {
            super(mContext, mAdapter);
            mAlbum = album;
        }

        @Override
        protected List<Photo> doInBackground() {
            return PhotoDao.loadPhotos(mAlbum);
        }

        @Override
        protected void itemClick(List<Photo> data, int position) {
            Intent intent = new Intent(mContext.get(), PhotoActivity.class);
            intent.putExtra(EXTRA_OPEN_PHOTO, data.get(position));
            mContext.get().startActivity(intent);
        }
    }
}
