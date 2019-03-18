package com.ptit.filmdictionary.data.source.local.contract;

import android.provider.BaseColumns;

public class FavoriteContract {
    public static class FavoriteEntry implements BaseColumns{
        public static final String TABLE_FAVORITE = "favorite";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_MOVIE = "movie";
        public static final String COLUMN_NAME_VOTE = "vote";
        public static final String COLUMN_NAME_POSTER = "poster_path";
        public static final String COLUMN_NAME_RELEASE_DATE = "release_date";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
    }
}
