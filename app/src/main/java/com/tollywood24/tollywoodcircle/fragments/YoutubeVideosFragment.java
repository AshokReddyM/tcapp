package com.tollywood24.tollywoodcircle.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import android.widget.Toast;


import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.VideoItem;
import com.tollywood24.tollywoodcircle.adapters.VideosListAdapter;
import com.tollywood24.tollywoodcircle.other.YoutubeConnectorNav;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class YoutubeVideosFragment extends Fragment {

    DBTollywoodVideos dbTollywoodVideos;
    VideosListAdapter adapter;
    private RecyclerView videosList;
    private List<VideoItem> searchResults;
    private String[] mVideoChannelsList;
    private List<VideoItem> totalSearchResults;
    private boolean previouslyStarted;


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


        totalSearchResults = new ArrayList<>();
        dbTollywoodVideos = new DBTollywoodVideos(getActivity());

        mVideoChannelsList = new String[]{
                "UCNApqoVYJbYSrni4YsbXzyQ",
                "UCULLmfWqhMOeiWEV6hkMSCg",
                "UCfwwVv0Azcj62KjUiPJxs9w",
                "UCqV5ln1nuIhyn6ywJIRxD3Q",
                "UCKKgnZJoDHChxAuWLTwvVpw",
                "UCintIUOJEktQBfhEI9XXpuw",
                "UCaH3cLxXu9nO0uloW0L46jQ",
                "UCAQk_zAuaksl_3p6lDmaNUQ",
                "UC08248zN4JDWJm5wRljr_2Q",
                "UCnoqvTW4YZExfDeq7_Wmd-w",
                "UC2YweMjOCBld2W4Mnlf-0tg",
                "UCniWAwnXdLHcWktKzZ0bK0g",
                "UCXK6CZiU3AaWECNAC7ekrjg",
                "UCnkJqM6PA4WE8QQouruInAw",
                "UClsO8HSjyPiSNEJknjE-6jQ",
                "UCRPs8mBbReNzkwYZTg-ZSuA",
                "UC_TzPHpxKxVD2H2Ia9tWTcA",
                "UCt5rjohPa7ue6n9x8qDnREA",
                "UCIeNlITYK46VkR7yIuTL8GQ",
                "UCSCMq5lBO2siZ8eA9gF-LMw",
                "UCzKVZcwsxOUlodqqVNh8Few",
                "UCYhE3LuAdW98CdwoemBsrdA",
                "UCDLxH9ONrpemu5LgYT2xMPg",
                "UCFgjPkfDFtyheoTndkbRWVQ",
                "UCuY_A2juyVptq8hWIhMnnSg",
                "UC60U1OtwT9Y6Buecc6P0L5g",
                "UCl1uVuvB6Bam18_BBLGHPBA",
                "UCg5NrxgQSWaK2N5Tbb1yk1Q",
                "UC2pmfLm7iq6Ov1UwYrWYkZA",
                "UC8JMSicN7gFHAr_NYNQhCqw",
                "UCi1m4TnmmXGUIY4roVtvjDA",
                "UCqR14yApZM2ZAOTY0qrKIGw",
                "UCjumsE_UKtOBXwvnNE2leeA",
                "UCNSR8Wdw1Pwuy0DAG06P4Mg",
                "UCg5NrxgQSWaK2N5Tbb1yk1Q",
                "UCXjhJbviBl0M4JAC3cxDXqA",
                "UC1TnQzkS5qsUswgp9uvS4Ig",
                "UCKlQqqGv0CPdLVsAqSlK6yw",
                "UCyKnG1FqbbhdEM1gMDVH_rA",
                "UCYdH-h-zZw-_g1P5lAVaFIw",
                "UCquZY3GHBO9tyuVXaDfQErw",
                "UCoY_p16GEbOQPgES1-wXpYg",
                "UCKKgnZJoDHChxAuWLTwvVpw",
        };


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {


            searchResults = new ArrayList<VideoItem>();
            videosList = (RecyclerView) getActivity().findViewById(R.id.videos_found);
            videosList.setHasFixedSize(true);
            // use a linear layout manager
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            videosList.setLayoutManager(layoutManager);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(videosList.getContext(),
                    layoutManager.getOrientation());
            videosList.addItemDecoration(dividerItemDecoration);


            boolean status = false;
            try {
                ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                    status = true;
                    new LoadingYoutubeVideos().execute();
                } else {
                    updateVideosFound();
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateVideosFound() {

        ArrayList<VideoItem> listOfVideos = dbTollywoodVideos.getDataList();
        int size = listOfVideos.size();


        while (size > 250) {
            dbTollywoodVideos.deleteFirstRow();
            size--;
        }


        if (listOfVideos.size() > 0) {
            adapter = new VideosListAdapter(listOfVideos);
            videosList.setAdapter(adapter);
        }

        adapter.notifyDataSetChanged();


    }

    public class LoadingYoutubeVideos extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            updateVideosFound();
        }

        @Override
        protected String doInBackground(String... strings) {
            YoutubeConnectorNav yc = new YoutubeConnectorNav(getActivity());
/*
                YoutubeVideosFragment.this.searchResults = yc.search(keyword);
*/

            for (int i = 0; i < mVideoChannelsList.length; i++) {
                searchResults = yc.search(mVideoChannelsList[i]);
                totalSearchResults.addAll(searchResults);

            }


            if (previouslyStarted) {
                for (int i = 0; i < totalSearchResults.size(); i++) {

                    VideoItem checkingData = dbTollywoodVideos.findLink(totalSearchResults.get(i).getVideo_id());

                    try {
                        if (checkingData == null) {
                            dbTollywoodVideos.addNews(totalSearchResults.get(i).getTitle(), totalSearchResults.get(i).getVideo_id(),
                                    totalSearchResults.get(i).getChannel_name(), totalSearchResults.get(i).getThumbnailURL());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } else {
                for (int i = 0; i < totalSearchResults.size(); i++) {
                    dbTollywoodVideos.addNews(totalSearchResults.get(i).getTitle(), totalSearchResults.get(i).getVideo_id(),
                            totalSearchResults.get(i).getChannel_name(), totalSearchResults.get(i).getThumbnailURL());
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateVideosFound();
        }

    }

}


