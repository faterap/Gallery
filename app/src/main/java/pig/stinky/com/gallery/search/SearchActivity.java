package pig.stinky.com.gallery.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import pig.stinky.com.gallery.LoadTask;
import pig.stinky.com.gallery.R;
import pig.stinky.com.gallery.bean.Photo;
import pig.stinky.com.gallery.db.PhotoDao;
import pig.stinky.com.gallery.photo.PhotoActivity;

import java.lang.ref.WeakReference;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText mPersonEt;
    private EditText mLocationEt;

    private RadioGroup mGroup;

    private enum Filter {
        PERSON, LOCATION, BOTH
    }

    // search parameters
    private Filter mState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mPersonEt = findViewById(R.id.et_person);
        mLocationEt = findViewById(R.id.et_location);
        mGroup = findViewById(R.id.radio_group);

        mGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_person_tag:
                    mState = Filter.PERSON;
                    break;
                case R.id.rb_location_tag:
                    mState = Filter.LOCATION;
                    break;
                case R.id.rb_both_tag:
                    mState = Filter.BOTH;
                    break;
                default:
                    mState = null;
                    break;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                // TODO: 11/12/18 start search task
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    static class SearchTask extends LoadTask<Photo> {

        private Filter mFilter;

        public SearchTask(WeakReference<Context> mContext, Filter filter) {
            super(mContext, mAdapter);
            mFilter = filter;
        }

        @Override
        protected List<Photo> doInBackground() {
            List<Photo> ret;
            switch (mFilter) {
                case PERSON:
                    ret = PhotoDao.
                case LOCATION:
                case BOTH:
                default:
                    break;
            }
            return;
        }

        @Override
        protected void itemClick(List<Photo> data, int position) {
            Intent intent = new Intent(mContext.get(), PhotoActivity.class);
            intent.putExtra(EXTRA_OPEN_ALBUM, data.get(position));
            mContext.get().startActivity(intent);
        }
    }
}
