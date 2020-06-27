package com.hotelrosana.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.regex.Pattern;

public class BookingActivity2ListAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String[] dummyArr;
    private String roomsType, numRooms, dateOfArrival, dateOfDeparture;
    private String fullName, mobile, email, nationality, amountPaid, bank, refNums;
    Button submitBtn;

    public BookingActivity2ListAdapter(Activity context, String[] dummyArr) {
        super(context, R.layout.list_item_activity_booking2, dummyArr);

        this.context = context;
        this.dummyArr = dummyArr;

        Intent i = this.context.getIntent();
        roomsType = i.getStringExtra("roomsType");
        numRooms = i.getStringExtra("numRooms");
        dateOfArrival = i.getStringExtra("dateOfArrival");
        dateOfDeparture = i.getStringExtra("dateOfDeparture");
    }

    @Override
    public View getView(int position, View view, ViewGroup root) {
        View rowView = null;
        LayoutInflater inflater = context.getLayoutInflater();

        if (position == 0) {
            rowView = inflater.inflate(R.layout.title_box, null, true);
            ((TextView) rowView.findViewById(R.id.titleText)).setText("Booking");

        } else {
            rowView = inflater.inflate(R.layout.list_item_activity_booking2, null, true);

            final Button submit = (Button) rowView.findViewById(R.id.submit);
            final EditText fullName = (EditText) rowView.findViewById(R.id.fullName);
            final EditText mobile = (EditText) rowView.findViewById(R.id.mobile);
            final EditText email = (EditText) rowView.findViewById(R.id.email);
            final EditText nationality = (EditText) rowView.findViewById(R.id.nationality);
            final EditText amountPaid = (EditText) rowView.findViewById(R.id.amountPaid);
            final EditText bank = (EditText) rowView.findViewById(R.id.bank);
            final EditText refNums = (EditText) rowView.findViewById(R.id.refNums);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view1) {
                    BookingActivity2ListAdapter.this.fullName = fullName.getText().toString();
                    BookingActivity2ListAdapter.this.mobile = mobile.getText().toString();
                    BookingActivity2ListAdapter.this.email = email.getText().toString();
                    BookingActivity2ListAdapter.this.nationality = nationality.getText().toString();
                    BookingActivity2ListAdapter.this.amountPaid = amountPaid.getText().toString();
                    BookingActivity2ListAdapter.this.bank = bank.getText().toString();
                    BookingActivity2ListAdapter.this.refNums = refNums.getText().toString();
                    BookingActivity2ListAdapter.this.submitBtn = submit;

                    String message = "Info:";
                    boolean noError = true;

                    // validate input
                    if (BookingActivity2ListAdapter.this.fullName.trim().isEmpty()) {
                        message += "\n- Full Name is required.";
                        noError = false;
                    } else {
                        if (!Pattern.matches("^([a-zA-Z]+[\\.\\s\\-']+)*[a-zA-Z]+$", BookingActivity2ListAdapter.this.fullName)) {
                            message += "\n- Invalid Full Name. Please do not put unnecessary characters.";
                            noError = false;
                        } else {
                            if (BookingActivity2ListAdapter.this.fullName.length() > 30) {
                                message += "\n- Too long Full Name. Only 30 characters are allowed.";
                                noError = false;
                            }
                        }
                    }

                    if (BookingActivity2ListAdapter.this.email.trim().isEmpty()) {
                        message += "\n- Email is required.";
                        noError = false;
                    } else {
                        if (!Pattern.matches("^(\\w+\\.)*\\w+@\\w+(\\.\\w+){1,2}$", BookingActivity2ListAdapter.this.email)) {
                            message += "\n- Invalid Email.";
                            noError = false;
                        } else {
                            if (BookingActivity2ListAdapter.this.email.length() > 100) {
                                message += "\n- Too long Email. Only 100 characters are allowed.";
                                noError = false;
                            }
                        }
                    }

                    if (BookingActivity2ListAdapter.this.mobile.trim().isEmpty()) {
                        message += "\n- Your Mobile phone number is required.";
                        noError = false;
                    } else {
                        if (!Pattern.matches("^[\\-\\s\\d\\+\\(\\)]{1,20}$", BookingActivity2ListAdapter.this.mobile)) {
                            message += "\n- Invalid Mobile phone number or the characters exceed 20.";
                            noError = false;
                        }
                    }

                    if (BookingActivity2ListAdapter.this.nationality.trim().isEmpty()) {
                        message += "\n- Your Nationality is required.";
                        noError = false;
                    } else {
                        if (!Pattern.matches("^[a-zA-Z\\-\\s\\+\\(\\)&\\.',]{1,50}$", BookingActivity2ListAdapter.this.nationality)) {
                            message += "\n- Invalid Nationality or the characters exceed 50.";
                            noError = false;
                        }
                    }

                    if (BookingActivity2ListAdapter.this.amountPaid.trim().isEmpty()) {
                        message += "\n- Amount of money that you paid is required.";
                        noError = false;
                    } else {
                        if (!Pattern.matches("^\\d{1,10}$", BookingActivity2ListAdapter.this.amountPaid)) {
                            message += "\n- Invalid Amount or the digits exceed 10.";
                            noError = false;
                        }
                    }

                    if (BookingActivity2ListAdapter.this.bank.trim().isEmpty()) {
                        message += "\n- Bank name is required.";
                        noError = false;
                    } else {
                        if (!Pattern.matches("^[a-zA-Z\\-\\s\\+\\(\\)&\\.',]{1,50}$", BookingActivity2ListAdapter.this.bank)) {
                            message += "\n- Invalid Bank name or the characters exceed 50.";
                            noError = false;
                        }
                    }

                    if (BookingActivity2ListAdapter.this.refNums.trim().isEmpty()) {
                        message += "\n- Reference Numbers is required.";
                        noError = false;
                    } else {
                        if (!Pattern.matches("^[a-zA-Z0-9]{1,20}$", BookingActivity2ListAdapter.this.refNums)) {
                            message += "\n- Invalid Reference numbers or the characters exceed 20.";
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
                urlParams = "step=save" +
                        "&rooms_type=" + URLEncoder.encode(BookingActivity2ListAdapter.this.roomsType, "UTF-8") +
                        "&num_rooms=" + URLEncoder.encode(BookingActivity2ListAdapter.this.numRooms, "UTF-8") +
                        "&arrival=" + URLEncoder.encode(BookingActivity2ListAdapter.this.dateOfArrival, "UTF-8") +
                        "&departure=" + URLEncoder.encode(BookingActivity2ListAdapter.this.dateOfDeparture, "UTF-8") +
                        "&full_name=" + URLEncoder.encode(BookingActivity2ListAdapter.this.fullName, "UTF-8") +
                        "&mobile=" + URLEncoder.encode(BookingActivity2ListAdapter.this.mobile, "UTF-8") +
                        "&email=" + URLEncoder.encode(BookingActivity2ListAdapter.this.email, "UTF-8") +
                        "&nationality=" + URLEncoder.encode(BookingActivity2ListAdapter.this.nationality, "UTF-8") +
                        "&amount_paid=" + URLEncoder.encode(BookingActivity2ListAdapter.this.amountPaid, "UTF-8") +
                        "&bank=" + URLEncoder.encode(BookingActivity2ListAdapter.this.bank, "UTF-8") +
                        "&ref_nums=" + URLEncoder.encode(BookingActivity2ListAdapter.this.refNums, "UTF-8");
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

            pdRing = new ProgressDialog(BookingActivity2ListAdapter.this.context);
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
                        flashMessage("Your booking was sent successfully. We shall email you a response as soon as possible.");

                        // disable the submit button
                        if (null != BookingActivity2ListAdapter.this.submitBtn) {
                            BookingActivity2ListAdapter.this.submitBtn.setEnabled(false);
                        }
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
