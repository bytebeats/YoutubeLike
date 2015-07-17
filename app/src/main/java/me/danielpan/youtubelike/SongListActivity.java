package me.danielpan.youtubelike;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import me.danielpan.youtubelike.abs.AbsActivity;

public class SongListActivity extends AbsActivity implements AdapterView.OnItemClickListener {
    private Toolbar toolbar;
    private ListView listView;
    private SongsAdapter adapter;
    private String[] songs;

    @Override
    protected void inflateContentView() {
        setContentView(R.layout.activity_song_list);
    }

    @Override
    protected void findViewsById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_appcompat);
        listView = (ListView) findViewById(R.id.list_view);
        songs = getResources().getStringArray(R.array.songs);
        adapter = new SongsAdapter(this, songs);
    }

    @Override
    protected void initWidgets() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showSimplestSnackbar(songs[position]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_song_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showCenterToast("Settings");
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class SongsAdapter extends BaseAdapter {
        private Context context;
        private String[] settings;

        public SongsAdapter(Context ctxt, String[] res) {
            context = ctxt;
            settings = res;
        }

        @Override
        public int getCount() {
            return settings.length;
        }

        @Override
        public String getItem(int position) {
            return settings[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.settings_item_layout, null, false);
                holder = new ViewHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.settings_item_text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(settings[position]);
            return convertView;
        }

        public class ViewHolder {
            TextView textView;
        }
    }
}
