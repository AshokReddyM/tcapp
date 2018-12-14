package com.tollywood24.tollywoodcircle.ui.news.news_list.fragment;

import com.tollywood24.tollywoodcircle.data.model.Upload;
import com.tollywood24.tollywoodcircle.ui.base.MvpView;

import java.util.ArrayList;

public interface DynamicNewsFragmentMvp extends MvpView{


    void onGettingLatestNews(ArrayList<Upload> uploads);

    void onError(String message);
}