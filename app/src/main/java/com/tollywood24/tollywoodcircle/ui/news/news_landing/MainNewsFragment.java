package com.tollywood24.tollywoodcircle.ui.news.news_landing;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.data.FireBaseDataManager;
import com.tollywood24.tollywoodcircle.data.model.CategoryResponse;
import com.tollywood24.tollywoodcircle.ui.base.BaseFragment;
import com.tollywood24.tollywoodcircle.ui.news.news_list.adapter.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainNewsFragment extends BaseFragment implements MainNewsMvp {
    private static final String TAG = "Invitation";

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.container)
    ViewPager pager;

    @Inject
    MainNewsPresenter presenter;

    ArrayList<CategoryResponse> categories = new ArrayList<>();
    private SectionsPagerAdapter mSectionsPagerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_news, container, false);
        fragmentComponent().inject(this);
        ButterKnife.bind(this, rootView);
        presenter.attachView(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), categories);
        pager.setAdapter(mSectionsPagerAdapter);
        tabs.setupWithViewPager(pager);
        presenter.syncCategories(FireBaseDataManager.getCategoriesRef("Telugu"));

        presenter.getCategoriesFromDB();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onGettingNewsCategories(List<CategoryResponse> categoriesArrayList) {
        categories.addAll(categoriesArrayList);
        mSectionsPagerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onError(String message) {
        Toast.makeText(getActivity(), "Something went wrong..", Toast.LENGTH_SHORT).show();
    }
}
