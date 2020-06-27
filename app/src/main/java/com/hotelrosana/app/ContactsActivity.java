package com.hotelrosana.app;

import static com.hotelrosana.app.ContactsActivityListAdapter.Contact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;


public class ContactsActivity extends AppCompatActivity {
    private ListView listContent;
    private Contact[] contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        contacts = new Contact[] {
                null,
                new Contact("+255 757 206 864", "hotel.rosana@gmail.com", "P. O. Box 9432, Uhuru Road, Dar es Salaam")
        };

        listContent = (ListView) findViewById(R.id.list_content);
        ContactsActivityListAdapter adapter = new ContactsActivityListAdapter(this, contacts);
        listContent.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Intent i;
        switch (menuItem.getItemId()) {
            case R.id.home:
                i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                break;
            case R.id.book:
                i = new Intent(getApplicationContext(), BookingActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
