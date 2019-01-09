package com.tollywood24.tollywoodcircle.ui.news.news_list.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.data.model.Post;
import com.tollywood24.tollywoodcircle.ui.base.BaseFragment;
import com.tollywood24.tollywoodcircle.ui.news.news_list.adapter.NewsListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DynamicNewsFragment extends BaseFragment implements DynamicNewsFragmentMvp {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    ArrayList<Post> dataModalsList;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    DynamicNewsFragmentPresenter presenter;

    private NewsListAdapter adapter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseRef;


    public DynamicNewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_telugu_new, container, false);
        fragmentComponent().inject(this);
        ButterKnife.bind(this, rootView);
        presenter.attachView(this);
        return rootView;
    }


    public static DynamicNewsFragment newInstance(int sectionNumber) {
        DynamicNewsFragment fragment = new DynamicNewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child("Telugu");

        dataModalsList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NewsListAdapter(dataModalsList, getActivity());
        mRecyclerView.setAdapter(adapter);

        swipeRefreshLayout.setRefreshing(true);
        presenter.getLatestNewsFromDB();
        presenter.syncLatestNews(mDatabaseRef);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    public void onGettingLatestNews(ArrayList<Post> uploads) {
        dataModalsList.addAll(uploads);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onError(String message) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
