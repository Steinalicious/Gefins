package com.gefins;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomGridAdapter extends ArrayAdapter {

    private int[] images = new int[]{};
    private String[] names = new String[]{};

    public CustomGridAdapter(@NonNull Context context, int resource,  int[] images, String[] names) {
        super(context, resource);
        this.images = images;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v;

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v = inflater.inflate(R.layout.grid_item_layout, null);

        TextView textView = (TextView) v.findViewById(R.id.ad_title);
        ImageView imageView = (ImageView) v.findViewById(R.id.img_ad);

        textView.setText(names[position]);
        imageView.setImageResource(images[position]);

        return v;

    }

}