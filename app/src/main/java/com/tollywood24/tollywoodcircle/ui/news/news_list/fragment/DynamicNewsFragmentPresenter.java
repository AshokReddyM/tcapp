package com.tollywood24.tollywoodcircle.ui.news.news_list.fragment;

import com.google.firebase.database.DatabaseReference;
import com.tollywood24.tollywoodcircle.data.DataManager;
import com.tollywood24.tollywoodcircle.data.model.Post;
import com.tollywood24.tollywoodcircle.data.model.Upload;
import com.tollywood24.tollywoodcircle.ui.base.BasePresenter;
import com.tollywood24.tollywoodcircle.utils.RxUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DynamicNewsFragmentPresenter extends BasePresenter<DynamicNewsFragmentMvp> {
    private final DataManager mDataManager;
    private Disposable mDisposable;

    @Inject
    public DynamicNewsFragmentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(DynamicNewsFragmentMvp mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }


    public void getLatestNews(DatabaseReference database) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        mDataManager.getLatestNews(database)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<Post>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(ArrayList<Post> posts) {
                        getMvpView().onGettingLatestNews(posts);
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
