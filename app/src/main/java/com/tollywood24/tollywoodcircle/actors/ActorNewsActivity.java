package com.tollywood24.tollywoodcircle.actors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.Upload;
import com.tollywood24.tollywoodcircle.adapters.RssFeedListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActorNewsActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    @BindView(R.id.actor_list_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.back_web_view)
    ImageView back;
    @BindView(R.id.post_heading_toolbar)
    TextView title;
    String FAVORITES;
    String[] splited;
    ArrayList<Upload> searchedList;
    private ArrayList<Upload> previousDataModalsList;
    private String mSearchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_news);

        AdView mAdView = (AdView) findViewById(R.id.adView_player);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        previousDataModalsList = new ArrayList<>();

        ButterKnife.bind(this);

        searchedList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        mSearchName = intent.getStringExtra("SearchName");
        splited = mSearchName.split("\\s+");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        title.setText(mSearchName + " news");


        SharedPreferences settings;
        List favorites;
        settings = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Upload[] favoriteItems = gson.fromJson(jsonFavorites, Upload[].class);
            favorites = Arrays.asList(favoriteItems);
            int i = 0;


            while (i < favorites.size()) {
                Upload upload=(Upload) favorites.get(i);
                String link=upload.getLink();
                if(link.contains(mSearchName)) {
                    previousDataModalsList.add((Upload) favorites.get(i));
                }
                i++;
            }
        }

        mRecyclerView.setAdapter(new RssFeedListAdapter(previousDataModalsList, ActorNewsActivity.this));
    }



}
