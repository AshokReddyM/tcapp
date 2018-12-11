package com.tollywood24.tollywoodcircle.ui.local_video_player;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.ui.base.BaseActivity;

public class VideoActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        VideoView videoView = new VideoView(this);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Uri.parse(getIntent().getStringExtra("video_path")));
        videoView.requestFocus();
        videoView.start();

    }
}
