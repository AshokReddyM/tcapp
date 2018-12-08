package com.tollywood24.tollywoodcircle.adapters;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.InterstitialAd;
import com.tollywood24.tollywoodcircle.utils.NetworkUtil;
import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.Upload;
import com.squareup.picasso.Picasso;
import com.tollywood24.tollywoodcircle.webview.PostDetailsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class RssFeedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int DEFAULT_VIEW_TYPE = 1;
    private CustomTabsServiceConnection mCustomTabsServiceConnection;
    private boolean warmedUp;
    private ArrayList<Upload> mRssFeedModels;
    private Activity mActivity;
    private TextView website;
    private CustomTabsIntent.Builder builder;
    private CustomTabsIntent customTabsIntent;
    private CustomTabsClient mClient;
    private CustomTabsSession mCustomTabsSession;
    private InterstitialAd mInterstitialAd;
    int mPosition;


    public RssFeedListAdapter(ArrayList<Upload> rssFeedModels, Activity activity) {
        mRssFeedModels = rssFeedModels;
        this.mActivity = activity;



    }


    @Override
    public int getItemViewType(int position) {
        return DEFAULT_VIEW_TYPE;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {


        switch (type) {
            default:
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_list, parent, false);
                return new FeedModelViewHolder(v1);
        }


    }


    public String gettingCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return sdf.format(calendar.getTime());
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
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

                    Picasso.with(mActivity)
                            .load(feedModel.getImageUrl())
                            .placeholder(R.drawable.placeholder)
                            // optional
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
                postDate.setText(String.valueOf(secs) + " sec");

            } else if (difference > 60000 && difference < 3600000) {

                int minutes = (int) ((difference / (1000 * 60)) % 60);
                postDate.setText(String.valueOf(minutes) + " m");

            } else if (difference > 3600000 && difference < 86400000) {

                int hours = (int) ((difference / (1000 * 60 * 60)) % 24);
                postDate.setText(String.valueOf(hours) + " h");

            } else if (difference > 86400000) {

                int days = (int) ((difference / (1000 * 60 * 60 * 24)));
                postDate.setText(String.valueOf(days) + " days");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        title.setText(feedModel.getTitle());


        website = (TextView) feedModelViewHolder.rssFeedView.findViewById(R.id.post_from_web_link);

        String aux[] = feedModel.getLink().split("/");
        website.setText(aux[1] + " " + aux[2]);

        feedModelViewHolder.rssFeedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aux[] = feedModel.getLink().split("/");

                if (NetworkUtil.getConnectivityStatusString(mActivity).equals("true")) {

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
        private View rssFeedView;

        public FeedModelViewHolder(View v) {
            super(v);
            rssFeedView = v;
        }


    }
}

