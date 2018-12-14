package com.tollywood24.tollywoodcircle.ui.news.news_list.adapter;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.data.model.NewsViewTypeModel;
import com.tollywood24.tollywoodcircle.data.model.Upload;
import com.tollywood24.tollywoodcircle.ui.news.news_list.PostDetailsActivity;
import com.tollywood24.tollywoodcircle.utils.NetworkUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int DEFAULT_VIEW_TYPE = 1;
    private ArrayList<Upload> mRssFeedModels;
    private Activity mActivity;
    private TextView website;


    public NewsListAdapter(ArrayList<Upload> rssFeedModels, Activity activity) {
        mRssFeedModels = rssFeedModels;
        this.mActivity = activity;


    }

    @Override
    public int getItemViewType(int position) {

        return NewsViewTypeModel.SINGLE_TYPE;

/*        if (position % 5 == 1) {
            return NewsViewTypeModel.AD_TYPE;

        } else {
            return NewsViewTypeModel.SINGLE_TYPE;
        }*/
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case NewsViewTypeModel.SINGLE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_list, parent, false);
                return new FeedModelViewHolder(view);
            case NewsViewTypeModel.AD_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_ad_type_row, parent, false);
                return new AdViewHolder(view);
        }
        return null;
    }


    public String gettingCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return sdf.format(calendar.getTime());
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {




        if (holder.getItemViewType() == NewsViewTypeModel.SINGLE_TYPE) {

            FeedModelViewHolder feedModelViewHolder;
            final Upload feedModel;
            if (!(holder instanceof FeedModelViewHolder)) {
                return;
            }


            feedModelViewHolder = (FeedModelViewHolder) holder;
            feedModel = (Upload) mRssFeedModels.get(position);


            TextView title = ((TextView) feedModelViewHolder.rssFeedView.findViewById(R.id.titleText));
            TextView postDate = ((TextView) feedModelViewHolder.rssFeedView.findViewById(R.id.post_postDate));
            ImageView postImg = (ImageView) feedModelViewHolder.rssFeedView.findViewById(R.id.img);
            postImg.setImageResource(R.drawable.placeholder);


            try {
                if (!feedModel.getTitle().equals(null)) {
                    try {

                        Glide.with(mActivity)
                                .load(feedModel.getImageUrl())
                                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                                        .transforms(new CenterCrop(), new RoundedCorners(8)).placeholder(R.drawable.plain_background))
                                .into(postImg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    postImg.setVisibility(View.GONE);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");

            Date date1 = null;
            try {
                String postTime = feedModel.getPostTime();
                date1 = format.parse(postTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date date2 = null;
            try {
                date2 = format.parse(gettingCurrentTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }


            try {
                long difference = date2.getTime() - date1.getTime();
                if (difference > 0 && difference < 60000) {

                    int secs = (int) ((difference / (1000 * 60)));
                    postDate.setText(String.format("%s sec ago", String.valueOf(secs)));

                } else if (difference > 60000 && difference < 3600000) {

                    int minutes = (int) ((difference / (1000 * 60)) % 60);
                    postDate.setText(String.format("%s m ago", String.valueOf(minutes)));

                } else if (difference > 3600000 && difference < 86400000) {

                    int hours = (int) ((difference / (1000 * 60 * 60)) % 24);
                    postDate.setText(String.format("%s h ago", String.valueOf(hours)));

                } else if (difference > 86400000) {

                    int days = (int) ((difference / (1000 * 60 * 60 * 24)));
                    postDate.setText(String.format("%s days ago", String.valueOf(days)));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            title.setText(Html.fromHtml(feedModel.getTitle()));


            website = (TextView) feedModelViewHolder.rssFeedView.findViewById(R.id.post_from_web_link);

            String aux[] = feedModel.getLink().split("/");
            website.setText(String.format("%s %s", aux[1], aux[2]));

            feedModelViewHolder.rssFeedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String aux[] = feedModel.getLink().split("/");

                    if (NetworkUtil.isNetworkConnected(mActivity)) {

                        Intent intent = new Intent(mActivity, PostDetailsActivity.class);
                        intent.putExtra("dataSetBroadCast", feedModel);
                        intent.putExtra("tag", "0");
                        mActivity.startActivity(intent);
                        mActivity.overridePendingTransition(R.anim.enter, R.anim.exit);

                    } else {
                        Toast.makeText(mActivity, "Please Check Internet Connection and TRY AGAIN", Toast.LENGTH_SHORT).show();
                    }


                }
            });

        } else {
        }


    }


    private PendingIntent createPendingShareIntent() {
        Intent actionIntent = new Intent(Intent.ACTION_SEND);
        actionIntent.setType("text/plain");
        actionIntent.putExtra(Intent.EXTRA_TEXT, mRssFeedModels.get(0));
        PendingIntent intent = PendingIntent.getActivity(mActivity.getApplicationContext(), 0, actionIntent, 0);
        return intent;
    }


    @Override
    public int getItemCount() {
        return mRssFeedModels.size();
    }


    public static class FeedModelViewHolder extends RecyclerView.ViewHolder {
        View rssFeedView;

        public FeedModelViewHolder(View v) {
            super(v);
            rssFeedView = v;
        }


    }

    public static class AdViewHolder extends RecyclerView.ViewHolder {
        View adView;

        public AdViewHolder(View v) {
            super(v);
            adView = v;
        }


    }


}

