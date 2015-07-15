package me.danielpan.youtubelike;

import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import me.danielpan.youtubelike.abs.AbsActivity;
import me.danielpan.youtubelike.widget.ListenedScrollView;

public class NbaBriefActivity extends AbsActivity {
    private static final String TAG = NbaBriefActivity.class.getSimpleName();
    private ListenedScrollView scrollView;
    private Toolbar toolbar;

    @Override
    protected void inflateContentView() {

        setContentView(R.layout.activity_nba_brief);
    }

    @Override
    protected void findViewsById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_appcompat);
        scrollView = (ListenedScrollView) findViewById(R.id.listened_scroll_view);
    }

//    private float ratio = 1.0000001F;

    @Override
    protected void initWidgets() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        scrollView.setOnScrollChangeListener(new ListenedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                int toolbarHeight = toolbar.getHeight();
                Log.i(TAG, "l: " + l + " t: " + t + " oldl: " + oldl + " oldt: " + oldt);
                Log.i(TAG, "ScrollView Height:  " + scrollView.getHeight() + " Toolbar Height:  " + toolbarHeight + " TextView Height: " + findViewById(R.id.tv).getHeight());
                if (t - oldt > 0 && t < toolbarHeight) {
//                    ratio =ratio*ratio;
                    float scrolledRatio = (float) t / toolbarHeight;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                        toolbar.setAlpha(1.0F - scrolledRatio);
                    }
                }

                if (t - oldt < 0 && t < toolbarHeight) {
//                    ratio = ratio*ratio;
                    float scrolledRatio = (float) t / toolbarHeight;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                        toolbar.setAlpha(1.0F - scrolledRatio);
                    }
                }

            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nba_brief, menu);
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
