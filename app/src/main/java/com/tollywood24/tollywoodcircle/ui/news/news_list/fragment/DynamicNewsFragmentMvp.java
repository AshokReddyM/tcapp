package com.tollywood24.tollywoodcircle.ui.news.news_list.fragment;

import com.tollywood24.tollywoodcircle.data.model.Post;
import com.tollywood24.tollywoodcircle.data.model.Upload;
import com.tollywood24.tollywoodcircle.ui.base.MvpView;

import java.util.ArrayList;
import java.util.List;

public interface DynamicNewsFragmentMvp extends MvpView{


    void onGettingLatestNews(List<Post> uploads);

    void onError(String message);
}
