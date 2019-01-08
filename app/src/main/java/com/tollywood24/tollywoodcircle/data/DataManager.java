package com.tollywood24.tollywoodcircle.data;

import com.google.firebase.database.DatabaseReference;
import com.tollywood24.tollywoodcircle.data.local.DatabaseHelper;
import com.tollywood24.tollywoodcircle.data.local.PreferencesHelper;
import com.tollywood24.tollywoodcircle.data.model.CategoryResponse;
import com.tollywood24.tollywoodcircle.data.model.Post;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class DataManager {

    public final PreferencesHelper mPreferencesHelper;
    private final DatabaseHelper mDatabaseHelper;

    @Inject
    public DataManager(PreferencesHelper preferencesHelper, DatabaseHelper databaseHelper) {
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }


    public Observable<List<CategoryResponse>> getCategoriesFromDB() {
        return mDatabaseHelper.getCategoriesFromDB();
    }


    public Observable<List<CategoryResponse>> syncCategories(final DatabaseReference database) {
        return mDatabaseHelper.syncCategories(database);
    }


    public Observable<ArrayList<Post>> syncLatestNews(final DatabaseReference database) {
        return mDatabaseHelper.syncLatestNews(database);
    }


    public Observable<ArrayList<Post>> getLatestNewsFromDB() {
        return mDatabaseHelper.getLatestNewsFromDB();
    }


}
