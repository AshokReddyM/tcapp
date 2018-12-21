package com.tollywood24.tollywoodcircle.ui.news.news_landing;

import com.tollywood24.tollywoodcircle.data.model.CategoryResponse;
import com.tollywood24.tollywoodcircle.ui.base.MvpView;

import java.util.ArrayList;
import java.util.List;

public interface MainNewsMvp extends MvpView{
    void onGettingNewsCategories(List<CategoryResponse> categoriesArrayList);

    void onError(String message);

}
