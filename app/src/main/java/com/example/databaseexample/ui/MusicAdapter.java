package com.example.databaseexample.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.databaseexample.R;
import com.example.databaseexample.model.Collection1;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by sohammondal on 24/04/15.
 */
public class MusicAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<Collection1> collection1List;

    private WeakReference<Context> contextWeakReference;

    public MusicAdapter(final Context context, List<Collection1> collection1List) {
        layoutInflater = LayoutInflater.from(context);
        this.collection1List = collection1List;
        contextWeakReference = new WeakReference<Context>(context);
    }

    @Override
    public int getCount() {
        if (collection1List == null)
            return 0;
        return collection1List.size();
    }

    @Override
    public Collection1 getItem(int position) {
        return collection1List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        public TextView name;
        public TextView artist;
        public ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_music, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.item_music_name);
            viewHolder.artist = (TextView) convertView.findViewById(R.id.item_music_artist);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_music_image);
            convertView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.name.setText(getItem(position).getName().getText());
        viewHolder.artist.setText(getItem(position).getArtist());

        if (getItem(position).getImage().getSrc() != null) {
            Picasso.with(contextWeakReference.get()).load(getItem(position).getImage().getSrc()).into(viewHolder.imageView);
        }

        return convertView;
    }
}
