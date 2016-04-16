package com.andresrcb.gcmtest;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactChatAdapter extends ArrayAdapter<Contact> {
    public ContactChatAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Contact contact = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.text_item_name);
        TextView tvPhone = (TextView) convertView.findViewById(R.id.text_item_phone);
        tvPhone.setVisibility(View.INVISIBLE);
        // Populate the data into the template view using the data object
        tvName.setText(contact.name);
        // Return the completed view to render on screen
        return convertView;
    }
}

