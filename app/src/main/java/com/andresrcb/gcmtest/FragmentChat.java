package com.andresrcb.gcmtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentChat extends Fragment{

    ListView list_conversations;
    ArrayList<Contact> ArrayContacts;
    ContactChatAdapter myAdapter;

    public static FragmentChat newInstance() {
        FragmentChat fragment = new FragmentChat();
        return fragment;
    }

    public FragmentChat() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        //Test: create the fake contacts. We have to take them from the database
        ArrayContacts=new ArrayList<Contact>();
        ArrayContacts.add(new Contact("Sid"));
        ArrayContacts.add(new Contact("Andres"));
        ArrayContacts.add(new Contact("Dov"));

        list_conversations = (ListView) rootView.findViewById(R.id.list_conversations);
        myAdapter= new ContactChatAdapter(getContext(), ArrayContacts);
        list_conversations.setAdapter(myAdapter);//relacionar el adaptador con la listview.

        //PREST ANY ITEM
        list_conversations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent i = new Intent(getActivity(), ConversationActivity.class);
                startActivity(i);
                //SE DEBE PASAR LOS DATOS DEL USUARIO QUE ABRE LA CONVERSACION PARA GUARDAR EL HISTORIAL
            }
        });
        return rootView;
    }
}
