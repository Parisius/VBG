package com.kyberlabs.projetvbg.classes;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.kyberlabs.projetvbg.R;

import java.util.List;

/**
 * Created by Dario DONOU on 02/06/2017.
 */

public class CustomListDenonce extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ClassDenonce> musicItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListDenonce(Activity activity, List<ClassDenonce> movieItems) {
        this.activity = activity;
        this.musicItems = movieItems;
    }

    @Override
    public int getCount() {
        return musicItems.size();
    }

    @Override
    public Object getItem(int location) {
        return musicItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.model_denonce, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        // NetworkImageView thumbNail = (NetworkImageView) convertView
        //       .findViewById(R.id.thumbnail);
        TextView categorie = (TextView) convertView.findViewById(R.id.title_categorie);
        TextView description = (TextView) convertView.findViewById(R.id.title_description);
        TextView quartier = (TextView) convertView.findViewById(R.id.title_quartier);
        TextView date = (TextView) convertView.findViewById(R.id.title_date);


        // getting movie data for the row
        ClassDenonce m = musicItems.get(position);

        // thumbnail image
        // thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);


        categorie.setText(m.getCategorie());
        description.setText(m.getDescription());
        quartier.setText(m.getQuartier());
        date.setText(m.getDate());

        // rating nbdown
        // nbdown.setText(m.getNb_down());

        return convertView;
    }
}
