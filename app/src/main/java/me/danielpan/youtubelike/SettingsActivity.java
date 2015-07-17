package me.danielpan.youtubelike;

import android.content.Context;
import android.support.design.widget.Snackbar;
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

public class SettingsActivity extends AbsActivity implements AdapterView.OnItemClickListener {
    private Toolbar toolbar;
    private ListView listView;
    private SettingAdapter adapter;
    private String[] settings;

    @Override
    protected void inflateContentView() {
        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void findViewsById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_appcompat);
        listView = (ListView) findViewById(R.id.list_view);
        settings = getResources().getStringArray(R.array.settings_items);
        adapter = new SettingAdapter(this, settings);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void initWidgets() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(SettingsGeneralActivity.class);
                break;
            default:
                break;
        }
        Snackbar.make(findViewById(android.R.id.content), settings[position], Snackbar.LENGTH_SHORT).show();
    }


    private class SettingAdapter extends BaseAdapter {
        private Context context;
        private String[] settings;

        public SettingAdapter(Context ctxt, String[] res) {
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
