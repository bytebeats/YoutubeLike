package me.danielpan.youtubelike;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import me.danielpan.youtubelike.abs.AbsActivity;
import me.danielpan.youtubelike.frag.MusicFrag;
import me.danielpan.youtubelike.frag.NbaStarsFrag;
import me.danielpan.youtubelike.frag.VersionFrag;
import me.danielpan.youtubelike.transformer.DepthPageTransformer;
import me.danielpan.youtubelike.util.IoUtil;
import me.danielpan.youtubelike.util.Utils;

public class HomeActivity extends AbsActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;
    private CoordinatorLayout coordinatorLayout;
    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ContentAdapter adapter;
    private SearchView searchView;
    private FloatingActionButton fab;

    //
    private ImageView ivAvatar;
    private TextView tvEmail;
    private TextView tvName;

    private File avatarFile = new File(Environment.getExternalStorageDirectory(), "avatar.jpeg");

    @Override
    protected void inflateContentView() {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void findViewsById() {
        drawerLayout = (DrawerLayout) findViewById(R.id.home_drawer_layout);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.home_coordinator_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.home_appbar_layout);
        tabLayout = (TabLayout) findViewById(R.id.home_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.home_view_pager);
        navigationView = (NavigationView) findViewById(R.id.home_navigation_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar_appcompat);
        fab = (FloatingActionButton) findViewById(R.id.fab_home);
        //
        ivAvatar = (ImageView) findViewById(R.id.profile_avatar);
        tvEmail = (TextView) findViewById(R.id.profile_email);
        tvName = (TextView) findViewById(R.id.profile_name);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open_desc, R.string.drawer_close_desc) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.app_info);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(R.string.app_name);
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawers();
                }
            }
        };
    }

    @Override
    protected void initWidgets() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PlayActivity.class);
            }
        });

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        adapter = new ContentAdapter(getSupportFragmentManager(), this);
        if (Build.VERSION_CODES.HONEYCOMB >= Build.VERSION.SDK_INT) {
            viewPager.setPageTransformer(true, new DepthPageTransformer());
        }
        viewPager.setAdapter(adapter);
//        tabLayout.setTabsFromPagerAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                showCenterToast("You selected " + tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }
        });
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                showCenterToast("You selected " + tab.getText().toString());
//                viewPager.setCurrentItem(tab.getPosition(), true);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMaterialDialog("Change Header", "Are you sure ?", "Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.takeAPhoto(HomeActivity.this, Utils.CODE_TAKE_PHOTO, avatarFile);
                    }
                }, "Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.selectPicture(HomeActivity.this, Utils.CODE_PICK_PICTURE);
                    }
                }, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        onBackPressed();
                    }
                });
            }
        });
        try {
            if (avatarFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(avatarFile.getAbsolutePath());
                ivAvatar.setImageBitmap(bitmap);
            } else {
                avatarFile.createNewFile();
                ivAvatar.setImageResource(R.mipmap.header);
            }
        } catch (IOException e) {
            e.printStackTrace();
            ivAvatar.setImageResource(R.mipmap.header);
        }
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ProfileActivity.class);
            }
        });
    }

    @Override
    protected void loadData() {
        SharedPreferences sharedPreferences = getPref();
        String username = sharedPreferences.getString("username", "");
        String name = sharedPreferences.getString("name", "LeBron James");
        tvEmail.setText(username);
        tvName.setText(name);
    }

    private boolean isSearchViewClosed = true;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        final MenuItem searchItem = menu.findItem(R.id.home_action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Query here");
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                isSearchViewClosed = true;
                showCenterToast("You closed SearchView");
                searchView.onActionViewCollapsed();
                return true;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSearchViewClosed = false;
                showCenterToast("You clicked SearchView");

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hideKeyboard();
                showCenterToast(searchView.getQuery().toString());
                searchView.onActionViewCollapsed();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                showCenterToast(searchView.getQuery().toString());
                return false;
            }
        });
        searchView.setSuggestionsAdapter(null);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home_action_search) {
            return true;
        } else if (id == R.id.home_action_settings) {
            showCenterToast("Action Settings");
            startActivity(SettingsActivity.class);
            return true;
        } else if (id == R.id.home_action_privacy_policy) {
            showCenterToast("Action Privacy Policy");
            return true;
        } else if (id == R.id.home_action_help_feedback) {
            showCenterToast("Help & Feedback");
            return true;
        } else if (id == R.id.home_action_sign_out) {
            showCenterToast("Sign out");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawers();
            return;
        }
        if (!isSearchViewClosed) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_bar:
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Utils.CODE_TAKE_PHOTO) {
                Utils.cropImage(Uri.fromFile(avatarFile), this, Utils.CODE_CROP_IMAGE);
            } else if (requestCode == Utils.CODE_PICK_PICTURE) {
                String path = Utils.getSelectedImagePathFromUri(this, data.getData());
                Utils.cropImage(path, this, Utils.CODE_CROP_IMAGE);
            } else if (requestCode == Utils.CODE_CROP_IMAGE) {
                Bundle extras = data.getExtras();
                showCenterToast("Is Bundle null? " + (extras == null));
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    ivAvatar.setImageBitmap(photo); // display image in ImageView
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(avatarFile);
                        photo.compress(Bitmap.CompressFormat.JPEG, 100, fos);// (0-100)compressing file
                        fos.flush();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        IoUtil.closeSilently(fos);
                    }
                }
            }
        } else {
            finish();
        }
    }

    public class ContentAdapter extends FragmentPagerAdapter {
        private Fragment[] fragments;
        private String[] tabTitles;

        public ContentAdapter(FragmentManager fm, Context ctxt) {
            super(fm);
            fragments = new Fragment[]{VersionFrag.newInstance(), MusicFrag.newInstance(), NbaStarsFrag.newInstance()};
            tabTitles = ctxt.getResources().getStringArray(R.array.home_tab_titles);
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }


        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }
    }
}
