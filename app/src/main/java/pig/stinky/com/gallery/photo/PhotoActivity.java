package pig.stinky.com.gallery.photo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pig.stinky.com.gallery.AlbumHelper;
import pig.stinky.com.gallery.BaseAdapter;
import pig.stinky.com.gallery.R;
import pig.stinky.com.gallery.album.AlbumActivity;
import pig.stinky.com.gallery.bean.Album;
import pig.stinky.com.gallery.bean.Photo;
import pig.stinky.com.gallery.detail.PhotoDetailActivity;
import pig.stinky.com.gallery.utils.DialogHelper;

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
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Photo photo = mAlbum.getPhotos().get(position);
                Intent intent = new Intent(PhotoActivity.this, PhotoDetailActivity.class);
                intent.putExtra(EXTRA_OPEN_PHOTO, photo);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                            AlbumHelper.removeAlbum(mAlbum);
                        }
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
                            AlbumHelper.renameAlbum(mAlbum, et.getText().toString());
                        }
                    }
                });
                renameDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
