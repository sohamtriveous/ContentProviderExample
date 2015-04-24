package com.example.databaseexample;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.databaseexample.provider.SongsProvider;

public class MainActivity extends ActionBarActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.activity_main_listview);
        queryDatabase();


    }

    private class MyAsyncTask extends AsyncTask<Uri, Integer, Cursor> {

        // background thread
        @Override
        protected Cursor doInBackground(Uri... params) {
            Cursor cursor = getContentResolver().query(params[0], null, null, null, null);
            return cursor;
        }

        // ui thread, before execution
        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "Starting execution", Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        // ui thread post execute
        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    Toast.makeText(MainActivity.this, "Done execution", Toast.LENGTH_SHORT).show();
                    String name = cursor.getString(cursor.getColumnIndex(MyHelper.Songs.name));
                    String artist = cursor.getString(cursor.getColumnIndex(MyHelper.Songs.artist));
                    Log.d("MainActivity", "Cursor results: " + name + ", " + artist);
                } while (cursor.moveToNext());
            }
        }

        // ui thread during execute
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    private void queryDatabase() {
        new MyAsyncTask().execute(new Uri[] {SongsProvider.SONGS_URI});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
