package com.gefins;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import Entities.Item;

public class CustomGridAdapter  extends BaseAdapter {

    private List<Item> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomGridAdapter(Context aContext) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_item_layout, null);
            holder = new ViewHolder();
            holder.flagView = (ImageView) convertView.findViewById(R.id.img_ad);
            new DownloadImg(holder.flagView).execute("https://res.cloudinary.com/aso40/image/upload/v1554385218/avatars-000559149189-tawe7l-t500x500.jpg");
            holder.countryNameView = (TextView) convertView.findViewById(R.id.ad_title);
            holder.populationView = (TextView) convertView.findViewById(R.id.ad_zip);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = this.listData.get(position);
        holder.countryNameView.setText(item.getItemName());
        holder.populationView.setText("" + item.getZipcode());


        return convertView;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();

        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomGridView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    static class ViewHolder {
        ImageView flagView;
        TextView countryNameView;
        TextView populationView;
    }

}