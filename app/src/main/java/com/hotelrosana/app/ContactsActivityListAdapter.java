package com.hotelrosana.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class ContactsActivityListAdapter extends ArrayAdapter<ContactsActivityListAdapter.Contact> {

    private Activity context;
    private Contact[] contacts;

    public ContactsActivityListAdapter(Activity context, Contact[] contacts) {
        super(context, R.layout.list_item_activity_contacts, contacts);

        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public View getView(int position, View view, ViewGroup root) {
        View rowView = null;
        LayoutInflater inflater = context.getLayoutInflater();

       if (position == 0) {
            rowView = inflater.inflate(R.layout.title_box, null, true);
            ((TextView) rowView.findViewById(R.id.titleText)).setText("Contacts");

       } else {
            rowView = inflater.inflate(R.layout.list_item_activity_contacts, null, true);
            ((TextView) rowView.findViewById(R.id.mobile)).setText(contacts[position].mobile);
            ((TextView) rowView.findViewById(R.id.email)).setText(contacts[position].email);
            ((TextView) rowView.findViewById(R.id.address)).setText(contacts[position].address);
       }

        return rowView;
    }

    public static class Contact {
        public String mobile, email, address;

        public Contact(String mobile, String email, String address) {
            this.mobile = mobile;
            this.email = email;
            this.address = address;
        }
    }
}

