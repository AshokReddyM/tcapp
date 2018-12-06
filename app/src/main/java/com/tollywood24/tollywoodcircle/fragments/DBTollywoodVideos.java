package com.tollywood24.tollywoodcircle.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tollywood24.tollywoodcircle.VideoItem;

import java.util.ArrayList;

class DBTollywoodVideos extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "tcTollywoodVideos";
    // Contacts table name
    private static final String TABLE_NAME = "tc_videos_news_table";
    // Shops Table Columns names
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String VIDEO_ID = "video_id";
    private static final String CHANNEL_NAME = "channel_name";
    private static final String THUMBNAIL_IMG = "thumbnail";

    public DBTollywoodVideos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "create table tc_videos_news_table(id integer primary key, title text,video_id text,channel_name text,thumbnail text)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean addNews(String title, String video_id, String channel_name, String thumbnail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        values.put(VIDEO_ID, video_id);
        values.put(CHANNEL_NAME, channel_name);
        values.put(CHANNEL_NAME, channel_name);
        values.put(THUMBNAIL_IMG, thumbnail);
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
        return false;
    }


    public VideoItem findLink(String video_id) {
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + VIDEO_ID + " =  \"" + video_id + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        VideoItem videoItem = new VideoItem();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            videoItem.setVideo_id(cursor.getString(2));
            cursor.close();
        } else {
            videoItem = null;
        }
        db.close();
        cursor.close();
        return videoItem;
    }


    // Getting All Contacts
    public ArrayList<VideoItem> getDataList() {
        ArrayList<VideoItem> dbTollywoodVideosModalsList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM tc_videos_news_table ORDER BY id DESC;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                VideoItem videoItem = new VideoItem();
                videoItem.setId(cursor.getString(0));
                videoItem.setTitle(cursor.getString(1));
                videoItem.setVideo_id(cursor.getString(2));
                videoItem.setChannel_name(cursor.getString(3));
                videoItem.setThumbnailURL(cursor.getString(4));
                dbTollywoodVideosModalsList.add(videoItem);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return dbTollywoodVideosModalsList;
    }


    public void deleteFirstRow() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            String rowId = cursor.getString(cursor.getColumnIndex(ID));
            db.delete(TABLE_NAME, ID + "=?", new String[]{rowId});
        }
        cursor.close();
        db.close();
    }
}

