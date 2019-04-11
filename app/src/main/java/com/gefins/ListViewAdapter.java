package com.gefins;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ListViewAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;
    private String[] names = new String[]{};
    private String[] queue = new String[]{};
    private String[] imageUrls = new String[]{};

    public ListViewAdapter(Context context, int resource, String[] names, String[] imageUrls, String[] queue) {
        super(context, R.layout.list_item_layout, resource, imageUrls);

        this.context = context;
        this.names = names;
        this.queue = queue;
        this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.list_item_layout, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.textView_adTitle);
        TextView textView1 = (TextView) convertView.findViewById(R.id.textView_description);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView_ad);

        textView.setText(names[position]);
        textView1.setText("Fjöldi í röð: " + queue[position]);

        Picasso.with(getContext())
                .load(Uri.parse(imageUrls[position]))
                .placeholder(R.drawable.earth_color)
                .into(imageView);

        return convertView;
    }
}