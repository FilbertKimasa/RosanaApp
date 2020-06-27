package com.hotelrosana.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

public class BookingActivityListAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String[] dummyArr;
    private String roomsType, numRooms, dateOfArrival, dateOfDeparture;

    public BookingActivityListAdapter(Activity context, String[] dummyArr) {
        super(context, R.layout.list_item_activity_booking, dummyArr);

        this.context = context;
        this.dummyArr = dummyArr;
    }

    @Override
    public View getView(int position, View view, ViewGroup root) {
        View rowView = null;
        LayoutInflater inflater = context.getLayoutInflater();

        if (position == 0) {
            rowView = inflater.inflate(R.layout.title_box, null, true);
            ((TextView) rowView.findViewById(R.id.titleText)).setText("Booking");

        } else {
            rowView = inflater.inflate(R.layout.list_item_activity_booking, null, true);

            ArrayAdapter<CharSequence> roomsTypeAdapter = ArrayAdapter.createFromResource(context, R.array.rooms_type, android.R.layout.simple_spinner_item);
            roomsTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner roomsType = (Spinner) rowView.findViewById(R.id.roomsType);
            roomsType.setAdapter(roomsTypeAdapter);

            roomsType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (adapterView.getSelectedItemPosition()) {
                        case 0:
                            BookingActivityListAdapter.this.roomsType = "1";
                            break;
                        case 1:
                            BookingActivityListAdapter.this.roomsType = "2";
                            break;
                        case 2:
                            BookingActivityListAdapter.this.roomsType = "3";
                            break;
                        case 3:
                            BookingActivityListAdapter.this.roomsType = "4";
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    adapterView.setSelection(0);
                }
            });

            ArrayAdapter<CharSequence> numRoomsAdapter = ArrayAdapter.createFromResource(context, R.array.num_rooms, android.R.layout.simple_spinner_item);
            numRoomsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner numRooms = (Spinner) rowView.findViewById(R.id.numRooms);
            numRooms.setAdapter(numRoomsAdapter);

            numRooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (adapterView.getSelectedItemPosition()) {
                        case 0:
                            BookingActivityListAdapter.this.numRooms = "1";
                            break;
                        case 1:
                            BookingActivityListAdapter.this.numRooms = "2";
                            break;
                        case 2:
                            BookingActivityListAdapter.this.numRooms = "3";
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    adapterView.setSelection(0);
                }
            });

            Button submit = (Button) rowView.findViewById(R.id.submit);
            final EditText dateOfArrival = (EditText) rowView.findViewById(R.id.dateOfArrival);
            final EditText dateOfDeparture = (EditText) rowView.findViewById(R.id.dateOfDeparture);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view1) {
                    BookingActivityListAdapter.this.dateOfArrival = dateOfArrival.getText().toString();
                    BookingActivityListAdapter.this.dateOfDeparture = dateOfDeparture.getText().toString();

                    String message = "Info:";
                    boolean noError = true;

                    // validate input

                    if (BookingActivityListAdapter.this.dateOfArrival.trim().isEmpty()) {
                        message += "\n- Date of Arrival is required.";
                        noError = false;
                    } else {
                        try {
                            BookingActivityListAdapter.this.dateOfArrival = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("dd-MM-yyyy").parse(BookingActivityListAdapter.this.dateOfArrival));
                            dateOfArrival.setText(BookingActivityListAdapter.this.dateOfArrival);
                        } catch(ParseException ex) {
                            message += "\n- Invalid Date of Arrival. Make sure it is in the format of dd-mm-yyyy.";
                            noError = false;
                        }
                    }

                    if (BookingActivityListAdapter.this.dateOfDeparture.trim().isEmpty()) {
                        message += "\n- Date of Departure is required.";
                        noError = false;
                    } else {
                        try {
                            BookingActivityListAdapter.this.dateOfDeparture = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("dd-MM-yyyy").parse(BookingActivityListAdapter.this.dateOfDeparture));
                            dateOfDeparture.setText(BookingActivityListAdapter.this.dateOfDeparture);
                        } catch(ParseException ex) {
                            message += "\n- Invalid Date of Departure. Make sure it is in the format of dd-mm-yyyy.";
                            noError = false;
                        }
                    }

                    if (!noError) {
                        flashMessage(message);
                    } else {
                        // send post request to server
                        new CallAPI().execute("http://192.168.43.71:8080/RosanaHotel/api/book/");
                    }
                }
            });
        }

        return rowView;
    }


    private class CallAPI extends AsyncTask<String, Integer, String> {
        private ProgressDialog pdRing;

        @Override
        protected String doInBackground(String... params) {
            String message = "", urlParams = "";

            try {
                urlParams = "step=check-availability&rooms_type=" + URLEncoder.encode(BookingActivityListAdapter.this.roomsType, "UTF-8") + "&num_rooms=" + URLEncoder.encode(BookingActivityListAdapter.this.numRooms, "UTF-8");
            } catch(UnsupportedEncodingException ex) {
                // do nothing
            }

            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);

                OutputStream os = con.getOutputStream();
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"))) {
                    writer.write(urlParams);
                    writer.flush();
                }

                os.close();

                // get server response
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String buffer = null;

                    while((buffer = reader.readLine()) != null) {
                        response.append(buffer);
                    }

                    message = response.toString();
                }

                con.connect();

                return message;
            } catch(Exception ex) {
                return "error";
            }
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();

            pdRing = new ProgressDialog(BookingActivityListAdapter.this.context);
            pdRing.setMessage("Please wait ...");
            pdRing.show();
        }

        @Override
        public void onPostExecute(String response) {
            super.onPostExecute(response);

            pdRing.dismiss();

            if (response.equals("error")) {
                flashMessage("Something went wrong.");
            } else {
                // convert server response to json and take appropriate actions
                try {
                    JSONObject json = new JSONObject(response);
                    String message = json.getString("message");
                    String status = json.getString("status");
                    if (!status.equals("success")) {
                        flashMessage(message);
                    } else {
                        Intent i = new Intent(BookingActivityListAdapter.this.context, BookingActivity2.class);
                        i.putExtra("roomsType", BookingActivityListAdapter.this.roomsType);
                        i.putExtra("numRooms", BookingActivityListAdapter.this.numRooms);
                        i.putExtra("dateOfArrival", BookingActivityListAdapter.this.dateOfArrival);
                        i.putExtra("dateOfDeparture", BookingActivityListAdapter.this.dateOfDeparture);

                        BookingActivityListAdapter.this.context.startActivity(i);
                    }
                } catch(JSONException ex) {
                    // do nothing
                }
            }
        }
    }

    private void flashMessage(String message) {
        Pattern messageTitle = Pattern.compile("^Info:$");
        if (null == message || Pattern.matches(messageTitle.toString(), message)) return;
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
