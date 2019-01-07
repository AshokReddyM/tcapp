package com.tollywood24.tollywoodcircle.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.tollywood24.tollywoodcircle.data.model.CategoryResponse;
import com.tollywood24.tollywoodcircle.data.model.Post;

public class Db {

    public Db() {
    }


    public abstract static class NewsPostTable {

        public static final String TABLE_NAME = "news_posts_table";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CATEGORY_ID = "category_id";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_IMAGE_URL = "imageUrl";
        public static final String COLUMN_LINK = "link";
        public static final String COLUMN_POST_TIME = "postTime";
        public static final String COLUMN_TOTAL_VIEWS = "totalViews";
        public static final String COLUMN_UNIQUE_KEY = "unique_key";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER COLUMN_API_ID,"
                        + COLUMN_CATEGORY_ID + " VARCHAR,"
                        + COLUMN_DESCRIPTION + " VARCHAR,"
                        + COLUMN_IMAGE_URL + " VARCHAR,"
                        + COLUMN_LINK + " VARCHAR,"
                        + COLUMN_POST_TIME + " VARCHAR,"
                        + COLUMN_TOTAL_VIEWS + " VARCHAR,"
                        + COLUMN_UNIQUE_KEY + " VARCHAR(1)" + " ); ";


        public static ContentValues toContentValues(Post post) {

            ContentValues values = new ContentValues();
            values.put(COLUMN_CATEGORY_ID, post.getCategory_id());
            values.put(COLUMN_DESCRIPTION, post.getDescription());
            values.put(COLUMN_IMAGE_URL, post.getImageUrl());
            values.put(COLUMN_LINK, post.getLink());
            values.put(COLUMN_POST_TIME, post.getPostTime());
            values.put(COLUMN_TOTAL_VIEWS, post.getTotalViews());
            values.put(COLUMN_UNIQUE_KEY, post.getUnique_key());

            return values;

        }


        public static Post parseCursor(Cursor cursor) {
            Post post = new Post();
            post.setCategory_id(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID)));
            post.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
            post.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL)));
            post.setLink(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LINK)));
            post.setPostTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POST_TIME)));
            post.setTotalViews(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_VIEWS)));
            post.setUnique_key(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UNIQUE_KEY)));
            return post;
        }
    }


    public abstract static class CategoryListTable {

        public static final String TABLE_NAME = "categories_table";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CATEGORY_NAME = "name";
        public static final String COLUMN_CATEGORY_ID = "category_id";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER COLUMN_API_ID,"
                        + COLUMN_CATEGORY_NAME + " VARCHAR,"
                        + COLUMN_CATEGORY_ID + " VARCHAR," + " ); ";


        public static ContentValues toContentValues(CategoryResponse categoryResponse) {

            ContentValues values = new ContentValues();
            values.put(COLUMN_CATEGORY_ID, categoryResponse.getKey());
            values.put(COLUMN_CATEGORY_NAME, categoryResponse.getName());
            return values;

        }


        public static CategoryResponse parseCursor(Cursor cursor) {
            return new CategoryResponse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_NAME)));
        }
    }


}
