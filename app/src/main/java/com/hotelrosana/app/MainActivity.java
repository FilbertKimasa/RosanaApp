package com.hotelrosana.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView listContent;

    private String[] itemsHeadings = {
            null, null, null, null, "1. Standard", "2. Classic", "3. Superior", "4. Family Suite"
    };

    private String[] itemsTexts = {
            null,
            "The Hotel",
            "The area we are in is called India Street, Dar es Salaam, Tanzania.\n" +
                    "A warm and personal calm will greet you as you walk into\n" +
                    "your home-away-from-home in Dar es Salaam.\n" +
                    "Offering authentic Tanzanian hospitably, the hotel originally commenced\n" +
                    "operations in 2007, and stands as one of the city’s premier hotel for\n" +
                    "both business and leisure travelers.\n" +
                    "\n" +
                    "Ideally situated in the heart of Dar es Salaam, Rosana Hotel offers a great\n" +
                    "location with excellent value. It is strategically located in the city’s\n" +
                    "dynamic business, financial, government offices, shopping, entertainment\n" +
                    "and educational hubs and within walking distance\n" +
                    "of connection by boat to Zanzibar.",
            "Our Rooms",
            "For an exceptional value, we have a couple of standard\n" +
                    "rooms offering a comfortable accommodation.\n" +
                    "The rooms are equipped with double beds, and\n" +
                    "have private bathrooms.\n\n" +
                    "$80/night",
            "These rooms equipped with one Double bed offer a\n" +
                    "cozy simple setup for your stay.\n" +
                    "No frills, just simple comfort.\n\n" +
                    "$60/night",
            "Our Superior rooms have Queen beds and sofa beds.\n" +
                    "They feature high ceilings with many original\n" +
                    "architectural details and can sleep up to 3 people.\n\n" +
                    "$100/night",
            "Our Family Suite is ideal for families and large groups.\n" +
                    "The room includes one Queen bed, one double bed,\n" +
                    "and a sofa bed, and can accommodate a total of 6 people.\n" +
                    "You will enjoy the high ceilings, bright windows and original\n" +
                    "architecture in this spacious suite.\n\n" +
                    "$120/night"
    };

    private Integer[] itemsImgs = {
            R.drawable.logo,
            null,
            R.drawable.rh7,
            null,
            R.drawable.rh9,
            R.drawable.rh8,
            R.drawable.rh1,
            R.drawable.rh2
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listContent = (ListView) findViewById(R.id.list_content);
        MainActivityListAdapter adapter = new MainActivityListAdapter(this, itemsHeadings, itemsTexts, itemsImgs);
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
            case R.id.book:
                i = new Intent(getApplicationContext(), BookingActivity.class);
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
