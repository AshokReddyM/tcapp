package com.tollywood24.tollywoodcircle.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.tollywood24.tollywoodcircle.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerActivity extends YouTubeBaseActivity {

    String mVideoId, mTitle;
    @BindView(R.id.player_view)
    YouTubePlayerView playerView;
    @BindView(R.id.title_youtube_video)
    TextView title;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_player);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mVideoId = intent.getStringExtra("video_id");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }

        title.setText(mTitle);
        player();


        mAdView = (AdView) findViewById(R.id.adView_player);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }


    public void player() {


        playerView.initialize(getResources().getString(R.string.youtube_id), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {
                if (!restored) {
                    youTubePlayer.loadVideo(mVideoId);
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(PlayerActivity.this, getString(R.string.failed), Toast.LENGTH_LONG).show();
            }
        });


    }


}
