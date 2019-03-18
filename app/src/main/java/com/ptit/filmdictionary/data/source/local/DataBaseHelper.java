package com.ptit.filmdictionary.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.databinding.ObservableArrayList;

import com.ptit.filmdictionary.data.model.History;
import com.ptit.filmdictionary.data.model.HistoryEntity;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.local.contract.FavoriteContract.FavoriteEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "movie.db";
    public static final String TABLE_DROP = "DROP TABLE IF EXISTS";
    private static final String SELECTION_SUFFIX = " LIKE ?";
    private static final String SQL_CREATE_FAVORITE =
            String.format(Locale.US, "CREATE TABLE %s (%s INTEGER PRIMARY KEY," +
                            " %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                    FavoriteEntry.TABLE_FAVORITE,
                    FavoriteEntry.COLUMN_NAME_ID,
                    FavoriteEntry.COLUMN_NAME_MOVIE,
                    FavoriteEntry.COLUMN_NAME_VOTE,
                    FavoriteEntry.COLUMN_NAME_POSTER,
                    FavoriteEntry.COLUMN_NAME_RELEASE_DATE,
                    FavoriteEntry.COLUMN_NAME_OVERVIEW);
    private static final String SQL_CREATE_HISTORY =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT)",
                    HistoryEntity.TABLE_HISTORY,
                    HistoryEntity.ID,
                    HistoryEntity.TITLE);
    private static final String SQL_DELETE_FAVORITE =
            TABLE_DROP + FavoriteEntry.TABLE_FAVORITE;
    private static final String SQL_DELETE_HISTORY =
            TABLE_DROP + HistoryEntity.TABLE_HISTORY;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FAVORITE);
        db.execSQL(SQL_CREATE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_FAVORITE);
        db.execSQL(SQL_DELETE_HISTORY);
        onCreate(db);
    }

    public ObservableArrayList<Movie> getAllFavorite() {
        SQLiteDatabase db = getReadableDatabase();
        List<Movie> movies = new ArrayList<>();
        ObservableArrayList<Movie> moviesObservable = new ObservableArrayList<>();
        String[] projection = {
                FavoriteEntry.COLUMN_NAME_ID,
                FavoriteEntry.COLUMN_NAME_MOVIE,
                FavoriteEntry.COLUMN_NAME_VOTE,
                FavoriteEntry.COLUMN_NAME_POSTER,
                FavoriteEntry.COLUMN_NAME_RELEASE_DATE,
                FavoriteEntry.COLUMN_NAME_OVERVIEW
        };
        Cursor cursor = db.query(FavoriteEntry.TABLE_FAVORITE,
                projection,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null) cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor
                    .getColumnIndexOrThrow(FavoriteEntry.COLUMN_NAME_ID));
            String title = cursor.getString(cursor
                    .getColumnIndexOrThrow(FavoriteEntry.COLUMN_NAME_MOVIE));
            Double vote_average = cursor.getDouble(cursor
                    .getColumnIndexOrThrow(FavoriteEntry.COLUMN_NAME_VOTE));
            String poster_path = cursor.getString(cursor
                    .getColumnIndexOrThrow(FavoriteEntry.COLUMN_NAME_POSTER));
            String release_date = cursor.getString(cursor
                    .getColumnIndexOrThrow(FavoriteEntry.COLUMN_NAME_RELEASE_DATE));
            String overview = cursor.getString(cursor
                    .getColumnIndexOrThrow(FavoriteEntry.COLUMN_NAME_OVERVIEW));
            Movie movie = new Movie();
            movie.setId(id);
            movie.setTitle(title);
            movie.setVoteAverage(vote_average);
            movie.setPosterPath(poster_path);
            movie.setReleaseDate(release_date);
            movie.setOverview(overview);

            movies.add(movie);
            cursor.moveToNext();
        }
        moviesObservable.addAll(movies);
        return moviesObservable;
    }

    public boolean addFavorite(Movie movie) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavoriteEntry.COLUMN_NAME_ID, movie.getId());
        values.put(FavoriteEntry.COLUMN_NAME_MOVIE, movie.getTitle());
        values.put(FavoriteEntry.COLUMN_NAME_VOTE, movie.getVoteAverage());
        values.put(FavoriteEntry.COLUMN_NAME_POSTER, movie.getPosterPath());
        values.put(FavoriteEntry.COLUMN_NAME_RELEASE_DATE, movie.getReleaseDate());
        values.put(FavoriteEntry.COLUMN_NAME_OVERVIEW, movie.getOverview());
        long result = db.insert(FavoriteEntry.TABLE_FAVORITE, null, values);
        return (result > 0);
    }

    public boolean deleteFavorite(Movie movie) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = FavoriteEntry.COLUMN_NAME_ID + SELECTION_SUFFIX;
        String[] selectionArgs = {String.valueOf(movie.getId())};
        int result = db.delete(FavoriteEntry.TABLE_FAVORITE, selection, selectionArgs);
        return (result > 0);
    }

    public boolean isFavorite(int movieId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {FavoriteEntry.COLUMN_NAME_ID};
        String selection = FavoriteEntry.COLUMN_NAME_ID + SELECTION_SUFFIX;
        String[] selectionArgs = {String.valueOf(movieId)};
        Cursor cursor = db.query(FavoriteEntry.TABLE_FAVORITE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return (cursor != null && !cursor.isAfterLast());
    }
    
    public ObservableArrayList<History> getAllHistories() {
        SQLiteDatabase db = getReadableDatabase();
        List<History> histories = new ArrayList<>();
        ObservableArrayList<History> historiesObservable = new ObservableArrayList<>();
        String[] projection = {
                HistoryEntity.ID,
                HistoryEntity.TITLE
        };
        Cursor cursor = db.query(HistoryEntity.TABLE_HISTORY,
                projection,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null) cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor
                    .getColumnIndexOrThrow(HistoryEntity.ID));
            String title = cursor.getString(cursor
                    .getColumnIndexOrThrow(HistoryEntity.TITLE));
            History history = new History(id, title);
            histories.add(history);
            cursor.moveToNext();
        }
        historiesObservable.addAll(histories);
        return historiesObservable;
    }

    public boolean addedHistory(History history) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HistoryEntity.TITLE, history.getTitle());
        long result = db.insert(HistoryEntity.TABLE_HISTORY, null, values);
        return (result > 0);
    }

    public boolean deletedHistory(History history) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = HistoryEntity.ID + SELECTION_SUFFIX;
        String[] selectionArgs = {String.valueOf(history.getId())};
        int result = db.delete(HistoryEntity.TABLE_HISTORY, selection, selectionArgs);
        return (result > 0);
    }

}
