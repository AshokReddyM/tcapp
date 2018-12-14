package com.tollywood24.tollywoodcircle.ui.online.video_landing_page.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.data.model.VideoItem;
import com.tollywood24.tollywoodcircle.ui.online.videos_list.VideosListActivity;

import java.util.List;

/**
 * Created by ca6 on 7/3/18.
 */

public class VideosListAdapter extends RecyclerView.Adapter<VideosListAdapter.ViewHolder> {

    List<VideoItem> totalSearchResults;
    Context context;

    public VideosListAdapter(List<VideoItem> totalSearchResults) {
        this.totalSearchResults = totalSearchResults;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_video_block, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        final VideoItem searchResult = totalSearchResults.get(position);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideosListActivity.class);
                intent.putExtra("title", searchResult.getTitle());
                intent.putExtra("video_id", searchResult.getVideo_id());
                intent.putExtra("video_position", position);
                context.startActivity(intent);
            }
        });

        /*Glide.with(context).load(searchResult.getThumbnailURL()).into(holder.thumbnail);*/
        /*holder.title.setText(searchResult.getTitle());*/

    }

    @Override
    public int getItemCount() {
        return totalSearchResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnail;
        TextView title;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.youtube_thumbnail);
            title = (TextView) itemView.findViewById(R.id.video_title);
            view = itemView;

        }
    }
}
