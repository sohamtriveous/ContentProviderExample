package com.example.databaseexample;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.databaseexample.model.Collection1;
import com.example.databaseexample.model.MusicmatchReesponse;
import com.example.databaseexample.network.Music;
import com.example.databaseexample.ui.CircleTransform;
import com.example.databaseexample.ui.MusicAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity{
    private ListView listView;

    private MusicAdapter musicAdapter;
    private static int ID_MUSIC = 1;

    private List<Collection1> collection1List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.activity_main_listview);
        listView.setDivider(null);

        Music.getLatestMusic(new Callback<MusicmatchReesponse>() {
            @Override
            public void success(MusicmatchReesponse musicmatchReesponse, Response response) {
                Log.d("MusicMatch", "Number of results: " + musicmatchReesponse.getResults().getCollection1().size());
                collection1List = musicmatchReesponse.getResults().getCollection1();
                musicAdapter = new MusicAdapter(MainActivity.this, collection1List);
                listView.setAdapter(musicAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("MusicMatch", "error is " + error.getMessage());
            }
        });
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

    private class MusicCursorAdapter extends CursorAdapter {
        private LayoutInflater layoutInflater;

        public MusicCursorAdapter(Context context) {
            super(context, null, 0);
            layoutInflater = LayoutInflater.from(context);
        }

        private class ViewHolder {
            public TextView name;
            public TextView artist;
            public ImageView imageView;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View layout = layoutInflater.inflate(R.layout.item_music, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) layout.findViewById(R.id.item_music_name);
            viewHolder.artist = (TextView) layout.findViewById(R.id.item_music_artist);
            viewHolder.imageView = (ImageView) layout.findViewById(R.id.item_music_image);
            layout.setTag(viewHolder);

            return layout;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            viewHolder.name.setText(cursor.getString(cursor.getColumnIndex(MyHelper.Songs.name)));
            viewHolder.artist.setText(cursor.getString(cursor.getColumnIndex(MyHelper.Songs.artist)));
            String path = cursor.getString(cursor.getColumnIndex(MyHelper.Songs.url));
            Picasso.with(context).load(path).transform(new CircleTransform()).into(viewHolder.imageView);
        }
    }
}
