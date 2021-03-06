package me.danielpan.youtubelike;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import me.danielpan.youtubelike.abs.AbsActivity;
import me.danielpan.youtubelike.adapter.CstmAdapter;

public class OverlayProfileActivity extends AbsActivity {
    private CoordinatorLayout coordinatorLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ImageView ivBg;
    private RecyclerView recyclerView;
    private CstmAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void inflateContentView() {
        setContentView(R.layout.activity_overlay_profile);
    }

    @Override
    protected void findViewsById() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        ivBg = (ImageView) findViewById(R.id.profile_bg);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    @Override
    protected void initWidgets() {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);//R.drawable.abc_ic_ab_back_mtrl_am_alpha
        actionBar.setTitle(R.string.title_activity_profile);
        actionBar.setSubtitle("This is a subtitle example");
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setWindowTitle("What's WindowTitle?");
        actionBar.setShowHideAnimationEnabled(true);
        actionBar.setIcon(R.mipmap.header);
        actionBar.setDefaultDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

//        collapsingToolbarLayout.setTitle(getString(R.string.title_activity_profile));
//        collapsingToolbarLayout.setSoundEffectsEnabled(true);
//        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.GREEN);
//        collapsingToolbarLayout.setExpandedTitleColor(Color.BLUE);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {

            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int index = layoutManager.findFirstVisibleItemPosition();
                View view = layoutManager.findViewByPosition(index);
                CstmAdapter.ViewHolder myViewHolder = (CstmAdapter.ViewHolder) view.getTag();
                toolbar.setTitle(myViewHolder.textView.getText());
            }
        });
        adapter = new CstmAdapter(this, getResources().getStringArray(R.array.singers), -1);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
