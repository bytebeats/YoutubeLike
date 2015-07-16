package me.danielpan.youtubelike;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.MediaController;
import android.widget.VideoView;

import me.danielpan.youtubelike.adapter.CstmAdapter;

public class PlayActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    //    private RecyclerView recyclerView;
    private VideoView videoView;
    private MediaController mediaController;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        videoView = (VideoView) findViewById(R.id.video_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.big_buck_bunny));
        videoView.start();
        videoView.setOnCompletionListener(this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        String[] singers = getResources().getStringArray(R.array.singers);
        recyclerView.setAdapter(new CstmAdapter(this, singers, CstmAdapter.TYPE_MUSIC));
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        videoView.start();
    }
}
