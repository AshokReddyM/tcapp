package com.tollywood24.tollywoodcircle.ui.local_video_player;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.data.model.LocalVideo;
import com.tollywood24.tollywoodcircle.ui.base.BaseFragment;
import com.tollywood24.tollywoodcircle.utils.DateUtil;
import com.tollywood24.tollywoodcircle.utils.PermissionUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;

public class ListFragment extends BaseFragment {

    ArrayList<LocalVideo> videosList;

    @BindView(R.id.videos_list)
    RecyclerView videosRecyclerview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_videos_list, container, false);
        fragmentComponent().inject(this);
        ButterKnife.bind(this, rootView);
        videosList = new ArrayList<>();
        setupRecyclerView();
        return rootView;
    }

    private void setupRecyclerView() {
        videosRecyclerview.setLayoutManager(new LinearLayoutManager(videosRecyclerview.getContext()));

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Video.VideoColumns.DATA,
                MediaStore.Video.VideoColumns.TITLE,
                MediaStore.Video.VideoColumns.DURATION};

        Cursor c = getActivity().getContentResolver().query(uri, projection, null, null, null);
        int vidsCount = 0;
        if (c != null) {
            vidsCount = c.getCount();
            String data = DatabaseUtils.dumpCursorToString(c);
            Log.d("cursor", data);
            while (c.moveToNext()) {
                LocalVideo video = new LocalVideo();
                video.setPath(c.getString(0));
                video.setTitle(c.getString(1));
                video.setTime(c.getString(2));
                videosList.add(video);
                Log.d("cursor data", video.toString());

            }
            c.close();
        }

        videosRecyclerview.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(), videosList));

    }


    public class SimpleStringRecyclerViewAdapter extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private ArrayList<LocalVideo> mVideoList;


        public SimpleStringRecyclerViewAdapter(Context context, ArrayList<LocalVideo> videoList) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mVideoList = videoList;
            mBackground = mTypedValue.resourceId;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {


            VideoThumbLoader mVideoThumbLoader = new VideoThumbLoader();
            holder.mThumbnail.setTag(videosList.get(position).getPath());// binding imageview
            mVideoThumbLoader.showThumbByAsynctack(videosList.get(position).getPath(), holder.mThumbnail);

            holder.mVideoTitle.setText(videosList.get(position).getTitle());
            holder.mVideoTotalTime.setText(DateUtil.timeConversion(Integer.parseInt(videosList.get(position).getTime())));


            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), PlayerActivity.class);
                    intent.putExtra("video_uri", videosList.get(position).getPath());
                    startActivity(intent);
                    getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }
            });
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;

            public final View mView;
            public final ImageView mThumbnail;
            public final TextView mVideoTitle;
            public final TextView mVideoTotalTime;


            public ViewHolder(View view) {
                super(view);
                mView = view;

                mThumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                mVideoTitle = (TextView) view.findViewById(R.id.video_title);
                mVideoTotalTime = (TextView) view.findViewById(R.id.video_total_time);
            }

        }



        @Override
        public int getItemCount() {
            return videosList.size();
        }



    }



}