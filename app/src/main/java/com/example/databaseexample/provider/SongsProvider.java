package com.example.databaseexample.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.example.databaseexample.MyHelper;

/**
 * Created by sohammondal on 24/04/15.
 */
public class SongsProvider extends ContentProvider {
    public static final String AUTHORITY = "com.example.databaseexample.provider.songsprovider";
    public static final Uri SONGS_URI = Uri.parse("content://" + AUTHORITY + "/" + MyHelper.TABLE_NAME);

    private MyHelper myHelper;

    @Override
    public boolean onCreate() {
        myHelper = new MyHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = myHelper.getReadableDatabase();

        switch (sUriMatcher.match(uri)) {
            case SONG:
            case SONG_ID:
                qb.setTables(MyHelper.TABLE_NAME);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case SONG:
            case SONG_ID:
                return "vnd.android.cursor.item/vnd.example.songs";
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        String table = null;
        switch (sUriMatcher.match(uri)) {
            case SONG:
            case SONG_ID:
                table = MyHelper.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        long row = db.insert(table, null, values);

        if (row > 0) {
            Uri newUri = ContentUris.withAppendedId(SONGS_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        String table = null;
        switch (sUriMatcher.match(uri)) {
            case SONG:
            case SONG_ID:
                table = MyHelper.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        int count = db.delete(table, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        String table = null;
        switch (sUriMatcher.match(uri)) {
            case SONG:
            case SONG_ID:
                table = MyHelper.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        int count = db.update(table, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    private static UriMatcher sUriMatcher = buildUriMatcher();

    private static final int SONG = 100;
    private static final int SONG_ID = 101;

    /**
     * content://com.example.databaseexample.provider.songsprovider/mysongs
     * content://com.example.databaseexample.provider.songsprovider/mysongs/2
     *
     * @return
     */
    public static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, MyHelper.TABLE_NAME, SONG);
        uriMatcher.addURI(AUTHORITY, MyHelper.TABLE_NAME + "/*", SONG_ID);

        return uriMatcher;
    }


}
