package me.danielpan.youtubelike;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import me.danielpan.youtubelike.abs.AbsActivity;

public class VersionIntroductionActivity extends AbsActivity {
    private Toolbar toolbar;

    @Override
    protected void inflateContentView() {
        setContentView(R.layout.activity_version_introduction);

    }

    @Override
    protected void findViewsById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_appcompat);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_version_introduction, menu);
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
}
