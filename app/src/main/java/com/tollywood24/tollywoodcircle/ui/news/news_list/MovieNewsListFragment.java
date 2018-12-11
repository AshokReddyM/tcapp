package com.tollywood24.tollywoodcircle.ui.news.news_list;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.data.model.Upload;
import com.tollywood24.tollywoodcircle.ui.base.BaseFragment;
import com.tollywood24.tollywoodcircle.ui.news.news_list.adapter.RssFeedListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieNewsListFragment extends BaseFragment implements MovieNewsListFragmentMvp {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    ArrayList<Upload> dataModalsList;
    String FAVORITES;
    String TAG;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;

    @BindView(R.id.loader)
    ProgressBar loader;

    @Inject
    MovieNewsListFragmentPresenter presenter;


    private RssFeedListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_telugu_new, container, false);
        fragmentComponent().inject(this);
        ButterKnife.bind(this, rootView);
        presenter.attachView(this);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("tollywoodnews");

        dataModalsList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RssFeedListAdapter(dataModalsList, getActivity());
        mRecyclerView.setAdapter(adapter);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        presenter.getLatestNews(database);


    }


    @Override
    public void onGettingLatestNews(ArrayList<Upload> uploads) {
        dataModalsList.addAll(uploads);
        adapter.notifyDataSetChanged();
        loader.setVisibility(View.GONE);

    }

    @Override
    public void onError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}






