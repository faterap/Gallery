package pig.stinky.com.gallery.album;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pig.stinky.com.gallery.db.AlbumDao;
import pig.stinky.com.gallery.BaseAdapter;
import pig.stinky.com.gallery.LoadTask;
import pig.stinky.com.gallery.R;
import pig.stinky.com.gallery.bean.Album;
import pig.stinky.com.gallery.photo.PhotoActivity;
import pig.stinky.com.gallery.utils.DialogHelper;

import java.lang.ref.WeakReference;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {

    public static final String EXTRA_OPEN_ALBUM = "open_album";

    private RecyclerView mRecyclerview;
    private AlbumAdapter mAlbumAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecyclerview = findViewById(R.id.recycler_view);

        mAlbumAdapter = new AlbumAdapter(this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(manager);
        mRecyclerview.setAdapter(mAlbumAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAlbum();
    }

    private void refreshAlbum() {
        AlbumTask task = new AlbumTask(new WeakReference<Context>(this), mAlbumAdapter);
        task.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_album, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_album:
                View root = View.inflate(this, R.layout.dialog_create, null);
                final EditText et = root.findViewById(R.id.create);

                AlertDialog dialog = DialogHelper.buildCustomViewDialog(this, "Create Album", root, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2018/12/10 do it in thread
                        AlbumDao.createAlbum(new Album(et.getText().toString()));

                        refreshAlbum();
                    }
                });
                dialog.show();
                return true;
            case R.id.search_album:
                // TODO: 11/12/18 add search activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    static class AlbumTask extends LoadTask<Album> {
        public AlbumTask(WeakReference<Context> mContext, BaseAdapter<Album> mAdapter) {
            super(mContext, mAdapter);
        }

        @Override
        protected List<Album> doInBackground() {
            return AlbumDao.loadAlbum();
        }

        @Override
        protected void itemClick(List<Album> data, int position) {
            Intent intent = new Intent(mContext.get(), PhotoActivity.class);
            intent.putExtra(EXTRA_OPEN_ALBUM, data.get(position));
            mContext.get().startActivity(intent);
        }
    }
}
