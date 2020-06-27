package com.hotelrosana.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;


public class BookingActivity extends AppCompatActivity {
    private ListView listContent;
    private String[] dummyArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        dummyArr = new String[] { null, null };

        listContent = (ListView) findViewById(R.id.list_content);
        BookingActivityListAdapter adapter = new BookingActivityListAdapter(this, dummyArr);
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
            case R.id.contacts:
                i = new Intent(getApplicationContext(), ContactsActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
