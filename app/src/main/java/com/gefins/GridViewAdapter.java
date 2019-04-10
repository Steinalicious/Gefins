package com.gefins;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;
    private String[] names = new String[]{};
    private String[] imageUrls = new String[]{};

    public GridViewAdapter(Context context, int resource, String[] names, String[] imageUrls) {
        super(context, R.layout.grid_item_layout, resource, imageUrls);

        this.context = context;
        this.names = names;
        this.imageUrls = imageUrls;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.grid_item_layout, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.ad_title);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_ad);

        textView.setText(names[position]);
        Picasso.with(getContext())
                .load(Uri.parse(imageUrls[position]))
                .placeholder(R.drawable.earth_color)
                .into(imageView);

        return convertView;
    }
}