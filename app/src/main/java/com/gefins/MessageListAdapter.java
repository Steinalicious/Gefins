package com.gefins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MessageListAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;
    private String[] usernames = new String[]{};
    private String[] messages = new String[]{};

    public MessageListAdapter(Context context, int resource, String[] usernames, String[] messages) {
        super(context, R.layout.message_item_layout, resource);
        this.context = context;
        this.usernames = usernames;
        this.messages = messages;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.message_item_layout, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.message_username);
        TextView textView1 = (TextView) convertView.findViewById(R.id.message_body);

        textView.setText(usernames[position]);
        textView1.setText(messages[position]);

        return convertView;
    }
}
