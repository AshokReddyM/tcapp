package com.tollywood24.tollywoodcircle.ui.online.video_landing_page.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.data.model.NestedVideoItem;
import com.tollywood24.tollywoodcircle.data.model.VideoItem;
import com.tollywood24.tollywoodcircle.ui.online.video_landing_page.adapter.MainVideosListAdapter;
import com.tollywood24.tollywoodcircle.ui.online.video_landing_page.adapter.VideosListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class YoutubeVideosFragment extends Fragment {

    VideosListAdapter adapter;
    private RecyclerView videosList;
    private String[] mVideoChannelsList;
    private boolean previouslyStarted;
    private ArrayList<NestedVideoItem> nestedVideoItemArrayList;


    public YoutubeVideosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_youtube_news, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);

        if (previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
            edit.commit();
        }


        nestedVideoItemArrayList = new ArrayList<>();

        NestedVideoItem nestedVideoItem = new NestedVideoItem();

        VideoItem videoItem1 = new VideoItem();
        videoItem1.setVideo_id("0SPwwpruGIA");

        VideoItem videoItem2 = new VideoItem();
        videoItem2.setVideo_id("6rTS_Q5w6LI");

        VideoItem videoItem3 = new VideoItem();
        videoItem3.setVideo_id("0SPwwpruGIA");

        VideoItem videoItem4 = new VideoItem();
        videoItem4.setVideo_id("6rTS_Q5w6LI");


        List<VideoItem> totalSearchResults = new ArrayList<>();

        totalSearchResults.add(videoItem1);
        totalSearchResults.add(videoItem2);
        totalSearchResults.add(videoItem3);
        totalSearchResults.add(videoItem4);


        nestedVideoItem.setTitle("Trending telugu Movies");
        nestedVideoItem.setVideoItems(totalSearchResults);


        nestedVideoItemArrayList.add(nestedVideoItem);


        NestedVideoItem nestedVideoItem2 = new NestedVideoItem();


        VideoItem mvideoItem1 = new VideoItem();
        videoItem1.setVideo_id("0SPwwpruGIA");

        VideoItem mvideoItem2 = new VideoItem();
        videoItem2.setVideo_id("6rTS_Q5w6LI");

        VideoItem mvideoItem3 = new VideoItem();
        videoItem3.setVideo_id("0SPwwpruGIA");

        VideoItem mvideoItem4 = new VideoItem();
        videoItem4.setVideo_id("6rTS_Q5w6LI");


        List<VideoItem> totalSearchResults2 = new ArrayList<>();

        totalSearchResults2.add(mvideoItem1);
        totalSearchResults2.add(mvideoItem2);
        totalSearchResults2.add(mvideoItem3);
        totalSearchResults2.add(mvideoItem4);

        nestedVideoItem2.setTitle("Trending Hindi Movies");
        nestedVideoItem2.setVideoItems(totalSearchResults2);
        nestedVideoItemArrayList.add(nestedVideoItem2);


        NestedVideoItem nestedVideoItem3 = new NestedVideoItem();

        VideoItem nvideoItem1 = new VideoItem();
        videoItem1.setVideo_id("0SPwwpruGIA");

        VideoItem nvideoItem2 = new VideoItem();
        videoItem2.setVideo_id("6rTS_Q5w6LI");

        VideoItem nvideoItem3 = new VideoItem();
        videoItem3.setVideo_id("0SPwwpruGIA");

        VideoItem nvideoItem4 = new VideoItem();
        videoItem4.setVideo_id("6rTS_Q5w6LI");


        List<VideoItem> totalSearchResults3 = new ArrayList<>();

        totalSearchResults3.add(nvideoItem1);
        totalSearchResults3.add(nvideoItem2);
        totalSearchResults3.add(nvideoItem3);
        totalSearchResults3.add(nvideoItem4);

        nestedVideoItem3.setTitle("Trending Hollywood Movies");
        nestedVideoItem3.setVideoItems(totalSearchResults3);
        nestedVideoItemArrayList.add(nestedVideoItem3);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {

            videosList = (RecyclerView) getActivity().findViewById(R.id.videos_found);
            MainVideosListAdapter adapter = new MainVideosListAdapter(nestedVideoItemArrayList);
            videosList.setHasFixedSize(true);
            // use a linear layout manager
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            videosList.setLayoutManager(layoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(videosList.getContext(), layoutManager.getOrientation());
            videosList.addItemDecoration(dividerItemDecoration);
            videosList.setAdapter(adapter);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

