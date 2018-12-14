package com.tollywood24.tollywoodcircle.ui.online.video_landing_page.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.data.model.NestedVideoItem;
import com.tollywood24.tollywoodcircle.data.model.VideoItem;

import java.util.List;

/**
 * Created by ca6 on 7/3/18.
 */

public class MainVideosListAdapter extends RecyclerView.Adapter<MainVideosListAdapter.ViewHolder> {

    List<NestedVideoItem> totalSearchResults;
    Context context;

    public MainVideosListAdapter(List<NestedVideoItem> totalSearchResults) {
        this.totalSearchResults = totalSearchResults;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.inner_single_videos_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        String title = totalSearchResults.get(position).getTitle();
        List<VideoItem> searchResult = totalSearchResults.get(position).getVideoItems();

        holder.title.setText(title);
        VideosListAdapter adapter = new VideosListAdapter(searchResult);
        holder.innerRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        holder.innerRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        holder.innerRecyclerView.setAdapter(adapter);

        /*Glide.with(context).load(searchResult.getThumbnailURL()).into(holder.thumbnail);*/
        /*holder.title.setText(searchResult.getTitle());*/

    }

    @Override
    public int getItemCount() {
        return totalSearchResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView innerRecyclerView;
        TextView title;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            innerRecyclerView = (RecyclerView) itemView.findViewById(R.id.inner_videos_recycularview);
            title = (TextView) itemView.findViewById(R.id.video_block_caption);
            view = itemView;

        }
    }
}
