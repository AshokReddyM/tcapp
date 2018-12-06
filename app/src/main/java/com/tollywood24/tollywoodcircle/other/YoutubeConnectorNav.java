package com.tollywood24.tollywoodcircle.other;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.VideoItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by trainee on 7/18/2017.
 */

public class YoutubeConnectorNav {
    Context context;
    private YouTube youtube;
    private YouTube.Search.List query;
    private String duration;


    public YoutubeConnectorNav(Context context) {
        this.context = context;
        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {
            }
        }).setApplicationName("Youtube").build();

        try {
            query = youtube.search().list("id,snippet");
            query.setKey(context.getResources().getString(R.string.youtube_id));
            query.setType("video");
            query.setOrder("date");
            query.setMaxResults((long) 3);
            query.setFields("items(id/videoId,snippet/title,snippet/thumbnails/high/url)");
        } catch (IOException e) {
            Log.d("YC", "Could not initialize: " + e);
        }
    }


    public List<VideoItem> search(String keywords) {
        query.setChannelId(keywords);

        try {
            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();

            List<VideoItem> items = new ArrayList<VideoItem>();
            for (SearchResult result : results) {
                VideoItem item = new VideoItem();
                item.setTitle(result.getSnippet().getTitle());
                String url = result.getSnippet().getThumbnails().getHigh().getUrl();
                item.setThumbnailURL(url);
                item.setVideo_id(result.getId().getVideoId());
                items.add(item);
            }
            return items;
        } catch (IOException e) {
            Log.d("YC", "Could not search: " + e);
            return null;
        }
    }


    public Date currentTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();
        return date;
    }

}