package com.tollywood24.tollywoodcircle.ui.news.news_landing;

import com.google.firebase.database.DatabaseReference;
import com.tollywood24.tollywoodcircle.data.DataManager;
import com.tollywood24.tollywoodcircle.data.model.CategoryResponse;
import com.tollywood24.tollywoodcircle.ui.base.BasePresenter;
import com.tollywood24.tollywoodcircle.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainNewsPresenter extends BasePresenter<MainNewsMvp> {

    private final DataManager mDataManager;
    private Disposable mDisposable;

    @Inject
    public MainNewsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainNewsMvp mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }


    public void getCategories(DatabaseReference database) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        mDataManager.getCategories(database)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<CategoryResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(List<CategoryResponse> categoriesArrayList) {
                        getMvpView().onGettingNewsCategories(categoriesArrayList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().onError(e.getMessage());
                        e.printStackTrace();

                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}
