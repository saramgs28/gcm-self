package com.andresrcb.gcmtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentContacts extends Fragment {

    ListView list_contacts;
    ArrayList<Contact> ArrayContacts; //test
    ContactAdapter myAdapter;

    public static FragmentContacts newInstance() {
        FragmentContacts fragment = new FragmentContacts();
        return fragment;
    }

    public FragmentContacts() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        //Test: create the fake contacts. We have to take them from the database
        ArrayContacts=new ArrayList<Contact>();
        ArrayContacts.add(new Contact("SaraContact", "5512148288"));
        ArrayContacts.add(new Contact("SidContact", "5512148289"));

        list_contacts = (ListView) rootView.findViewById(R.id.list_contacts);
        myAdapter= new ContactAdapter(getContext(), ArrayContacts);
        list_contacts.setAdapter(myAdapter);//relacionar el adaptador con la listview.

        return rootView;
    }
}
