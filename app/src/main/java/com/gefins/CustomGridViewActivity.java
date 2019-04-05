package com.gefins;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomGridViewActivity extends BaseAdapter {

    private Context mContext;
    private final String[] gridViewString;
   // private final int[] gridViewImageId;
    private final String[] urls;

    public CustomGridViewActivity(Context context, String[] gridViewString, String[] urls) {
        mContext = context;
        this.urls = urls;
        this.gridViewString = gridViewString;

    }

    @Override
    public int getCount() {
        return gridViewString.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.gridview_item, null);
            TextView textViewAndroid = gridViewAndroid.findViewById(R.id.android_gridview_text);
            ImageView imageViewAndroid =  gridViewAndroid.findViewById(R.id.android_gridview_image);
            textViewAndroid.setText(gridViewString[i]);

            new DownloadImg(imageViewAndroid).execute(urls[0]);
            Log.d("URLS", urls[i]);
            //imageViewAndroid.setImage(gridViewImageId[i]);
        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }
}