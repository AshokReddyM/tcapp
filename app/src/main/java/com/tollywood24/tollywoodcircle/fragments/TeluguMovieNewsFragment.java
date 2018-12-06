package com.tollywood24.tollywoodcircle.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.Upload;
import com.tollywood24.tollywoodcircle.adapters.RssFeedListAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TeluguMovieNewsFragment extends Fragment {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    ArrayList<Upload> dataModalsList;
    String FAVORITES;
    String TAG;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeLayout;
    private ArrayList<Upload> previousDataModalsList;
    private CoordinatorLayout coordinatorLayout;
    private Snackbar snackbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_telugu_new, container, false);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dataModalsList = new ArrayList<>();
        previousDataModalsList = new ArrayList<>();

        ImageView facebook = (ImageView) getActivity().findViewById(R.id.facebook);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/191541087982907")));
                    Toast.makeText(getActivity(), "Please Like 'Rangastalam' Page", Toast.LENGTH_LONG).show();

                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });


        swipeLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_container);


        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorDimWhite));


        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                finalDataSetInAdapter();
                Toast.makeText(getContext(), "Latest Tollywood News Updated", Toast.LENGTH_SHORT).show();
            }
        });


        showSnackBar("తాజా వార్తలు తీసుకుంటున్నాము...");

        finalDataSetInAdapter();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataModalsList.clear();
    }


    public void finalDataSetInAdapter() {

        try {


            SharedPreferences settings;
            List favorites;
            settings = getActivity().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
            if (settings.contains(FAVORITES)) {
                String jsonFavorites = settings.getString(FAVORITES, null);
                Gson gson = new Gson();
                Upload[] favoriteItems = gson.fromJson(jsonFavorites, Upload[].class);
                favorites = Arrays.asList(favoriteItems);
                int i = 0;
                while (i < favorites.size()) {
                    previousDataModalsList.add((Upload) favorites.get(i));
                    i++;
                }
            }

            mRecyclerView.setAdapter(new RssFeedListAdapter(this.previousDataModalsList, getActivity()));

            final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("tollywoodnews");

            database.orderByKey().limitToLast(600).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataModalsList.clear();

                    for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {

                        if (dataSnapshot.hasChildren()) {
                            Upload dataModal = noteDataSnapshot.getValue(Upload.class);
                            if (getCountOfDays(dataModal.getPostTime()) < 10) {
                                dataModalsList.add(dataModal);
                            } else {
                                DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                                firstChild.getRef().removeValue();
                            }

                        }

                    }

                    Collections.reverse(dataModalsList);
                    recyclerViewAdapter(dataModalsList);

                    snackbar.dismiss();
                    swipeLayout.setRefreshing(false);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void recyclerViewAdapter(ArrayList<Upload> dataModalsList) {
        mRecyclerView.setAdapter(new RssFeedListAdapter(this.dataModalsList, getActivity()));
        storeFavorites(getContext(), dataModalsList);
    }


    public void storeFavorites(Context context, ArrayList<Upload> favorites) {

        try {
            SharedPreferences settings;
            SharedPreferences.Editor editor;
            settings = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
            editor = settings.edit();
            Gson gson = new Gson();
            String jsonFavorites = gson.toJson(favorites);
            editor.putString(FAVORITES, jsonFavorites);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int getCountOfDays(String pubDate) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        try {
            date = format.parse(pubDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date currentdate = new Date();
        long currentDateMilliSec = currentdate.getTime();
        long updateDateMilliSec = date.getTime();
        long diffDays = (currentDateMilliSec - updateDateMilliSec) / (24 * 60 * 60 * 1000);

        return ((int) diffDays);
    }


    public void showSnackBar(String text) {

        snackbar = Snackbar.make(coordinatorLayout, text, Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(Color.RED);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.DKGRAY);
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();

    }


}






