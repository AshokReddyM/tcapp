package com.tollywood24.tollywoodcircle.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tollywood24.tollywoodcircle.data.local.DatabaseHelper;
import com.tollywood24.tollywoodcircle.data.local.PreferencesHelper;
import com.tollywood24.tollywoodcircle.data.model.CategoryResponse;
import com.tollywood24.tollywoodcircle.data.model.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

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


    public Observable<ArrayList<Post>> getLatestNewsFromDB() {
        return mDatabaseHelper.getLatestNewsFromDB();
    }


    public Observable<List<Post>> syncNews(DatabaseReference database) {
        return getNewsFromFireBase(database).concatMap(new Function<List<Post>, ObservableSource<? extends List<Post>>>() {
            @Override
            public ObservableSource<? extends List<Post>> apply(List<Post> posts) throws Exception {
                return mDatabaseHelper.syncLatestNews(posts);
            }
        });

    }

    private Observable<List<Post>> getNewsFromFireBase(DatabaseReference database) {

        return Observable.create(emitter -> {
            final ArrayList<Post> dataModalsList = new ArrayList<>();
            database.child("News").limitToLast(600).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                        if (dataSnapshot.hasChildren()) {
                            Post dataModal = noteDataSnapshot.getValue(Post.class);
                            if (getCountOfDays(dataModal.getPostTime()) < 10) {
                                dataModalsList.add(dataModal);
                            } else {
                                DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                                firstChild.getRef().removeValue();
                            }
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        });

    }


    private int getCountOfDays(String pubDate) {
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


}
