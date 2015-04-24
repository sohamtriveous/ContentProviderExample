package com.example.databaseexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by sohammondal on 23/04/15.
 */
public class MyHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "music";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_NAME = "mysongs";

    public interface Songs {
        public String name = "name";
        public String artist = "artist";
        public String url = "url";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY," +
                    Songs.name + TEXT_TYPE + COMMA_SEP +
                    Songs.artist + TEXT_TYPE + COMMA_SEP +
                    Songs.url + TEXT_TYPE +
                    " )";

    public MyHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    private void initDatabase(final SQLiteDatabase sqLiteDatabase, final String tableName) {

        ContentValues values = new ContentValues();
        values.put(Songs.name, "In The End");
        values.put(Songs.artist, "Linkin Park");
        values.put(Songs.url, "http://upload.wikimedia.org/wikipedia/en/5/52/Linkin_in_the_end_single_cover.png");
        sqLiteDatabase.insert(tableName, null, values);
        values.put(Songs.name, "Numb");
        values.put(Songs.artist, "Linkin Park");
        values.put(Songs.url, "http://data1.blog.de/blog/s/spidouz/img/Linkin_Park.jpg");
        sqLiteDatabase.insert(tableName, null, values);
        values.put(Songs.name, "Breaking THe Habit");
        values.put(Songs.artist, "Linkin Park");
        values.put(Songs.url, "http://upload.wikimedia.org/wikipedia/en/e/ec/Linkin_park_breaking_the_habit.png");
        sqLiteDatabase.insert(tableName, null, values);
        values.put(Songs.name, "Unforgiven");
        values.put(Songs.artist, "Metallica");
        values.put(Songs.url, "http://www.lyrics007.com/images/cover_art/55/31/21");
        sqLiteDatabase.insert(tableName, null, values);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        initDatabase(db, TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
