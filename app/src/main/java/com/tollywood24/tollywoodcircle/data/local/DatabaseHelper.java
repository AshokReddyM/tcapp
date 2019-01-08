package com.tollywood24.tollywoodcircle.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.VisibleForTesting;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;
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
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class DatabaseHelper {

    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        this(dbOpenHelper, Schedulers.io());
    }

    @VisibleForTesting
    public DatabaseHelper(DbOpenHelper dbOpenHelper, Scheduler scheduler) {
        SqlBrite.Builder briteBuilder = new SqlBrite.Builder();
        mDb = briteBuilder.build().wrapDatabaseHelper(dbOpenHelper, scheduler);
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }


    public Observable<List<CategoryResponse>> getCategoriesFromDB() {
        return Observable.create(new ObservableOnSubscribe<List<CategoryResponse>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CategoryResponse>> e) throws Exception {
                if (e.isDisposed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                String query = "SELECT * FROM " + Db.CategoryListTable.TABLE_NAME;

                ArrayList<CategoryResponse> categoriesList = new ArrayList<>();

                try {
                    Cursor cursor = mDb.query(query);
                    if (cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                categoriesList.add(Db.CategoryListTable.parseCursor(cursor));
                            } while (cursor.moveToNext());
                        }

                        e.onNext(categoriesList);

                    }
                    transaction.markSuccessful();

                } catch (SQLiteException exception) {
                    e.onError(exception);
                    exception.printStackTrace();
                } finally {
                    transaction.end();
                    e.onComplete();
                }

            }
        });
    }

    public Observable<List<CategoryResponse>> syncCategories(final DatabaseReference database) {

        return Observable.create(new ObservableOnSubscribe<List<CategoryResponse>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<CategoryResponse>> e) throws Exception {
                if (e.isDisposed()) return;
                final BriteDatabase.Transaction transaction = mDb.newTransaction();
                mDb.delete(Db.CategoryListTable.TABLE_NAME, null);
                database.orderByKey().limitToLast(600).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            List<CategoryResponse> categoriesList = new ArrayList<>();
                            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                                if (dataSnapshot.hasChildren()) {
                                    String key = noteDataSnapshot.getKey();
                                    String value = (String) noteDataSnapshot.getValue();
                                    ContentValues values = Db.CategoryListTable.toContentValues(new CategoryResponse(value, key));
                                    mDb.insert(Db.CategoryListTable.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
                                    categoriesList.add(new CategoryResponse(value, key));
                                } else {
                                    DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                                    firstChild.getRef().removeValue();
                                }
                            }
                            e.onNext(categoriesList);
                            transaction.markSuccessful();
                        } catch (Exception ex) {
                            e.onError(ex);
                            transaction.close();
                            ex.printStackTrace();
                        } finally {
                            e.onComplete();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
            }
        });
    }

    public Observable<ArrayList<Post>> syncLatestNews(final DatabaseReference database) {
        return Observable.create(new ObservableOnSubscribe<ArrayList<Post>>() {
            @Override
            public void subscribe(final ObservableEmitter<ArrayList<Post>> e) throws Exception {
                if (e.isDisposed()) return;
                final BriteDatabase.Transaction transaction = mDb.newTransaction();

                database.child("News").limitToLast(600).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        try {
                            ArrayList<Post> dataModalsList = new ArrayList<>();
                            mDb.delete(Db.NewsTable.TABLE_NAME, null);

                            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                                if (dataSnapshot.hasChildren()) {
                                    Post dataModal = noteDataSnapshot.getValue(Post.class);
                                    if (getCountOfDays(dataModal.getPostTime()) < 10) {
                                        dataModalsList.add(dataModal);
                                        ContentValues values = Db.NewsTable.toContentValues(new Post(dataModal.getCategory_id(),
                                                dataModal.getDescription(), dataModal.getImageUrl(), dataModal.getLink(),
                                                dataModal.getPostTime(), dataModal.getTitle(), dataModal.getTotalViews(), dataModal.getUnique_key()));
                                        mDb.insert(Db.NewsTable.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
                                        dataModalsList.add(dataModal);
                                    } else {
                                        DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                                        firstChild.getRef().removeValue();
                                    }

                                }

                            }
                            e.onNext(dataModalsList);
                            transaction.markSuccessful();
                        } catch (Exception ex) {
                            e.onError(ex);
                            transaction.close();
                            ex.printStackTrace();
                        } finally {
                            e.onComplete();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

        });
    }


    public Observable<ArrayList<Post>> getLatestNewsFromDB() {
        return Observable.create(new ObservableOnSubscribe<ArrayList<Post>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<Post>> e) throws Exception {

                if (e.isDisposed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                String query = "SELECT * FROM " + Db.NewsTable.TABLE_NAME;

                ArrayList<Post> posts = new ArrayList<>();

                try {
                    Cursor cursor = mDb.query(query);
                    if (cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                posts.add(Db.NewsTable.parseCursor(cursor));
                            } while (cursor.moveToNext());
                        }

                        e.onNext(posts);

                    }
                    transaction.markSuccessful();

                } catch (SQLiteException exception) {
                    e.onError(exception);
                    exception.printStackTrace();
                } finally {
                    transaction.end();
                    e.onComplete();
                }

            }
        });
    }


    public int getCountOfDays(String pubDate) {
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
