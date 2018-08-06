package com.example.musta.calender;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView myDate,eventText;
    LinearLayout myEvent;
    Button btn,updateButton,deleteButton;
    StringRequest stringRequest;
    public RequestQueue queue;
    String result,text,title,start_date,start_time,end_date,end_time;
    public String searchDate="";
    String db = "http://marvelous-wind-cave-84354.herokuapp.com/api/v1/rendezvous_data.json";
    public static final String EXTRA_MESSAGE = "com.example.musta.calender.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Intent intent = getIntent();

        btn = (Button) findViewById(R.id.addBtn);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        myDate = (TextView) findViewById(R.id.myDate);
        myEvent = (LinearLayout) findViewById(R.id.myEvent);
        eventText = (TextView) findViewById(R.id.eventText);
        eventText.setMovementMethod(new ScrollingMovementMethod());
        updateButton = (Button) findViewById(R.id.updateButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        String keyword = intent.getStringExtra(CalendarActivity.EXTRA_MESSAGE);
        eventText.setText(keyword);

        queue = Volley.newRequestQueue(this);
        //   btn.on
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                final String monthString,day,month;
                if(i2 < 10)
                    day = 0 + "" + i2;
                else
                    day =""+ i2;
                if(i1+1 < 10)
                    month = 0+""+(i1+1);
                else
                    month = "" + (i1+1);

                searchDate = i + "-" + month + "-" + day;

                switch (i1+1){
                    case 1:  monthString = "January";
                        break;
                    case 2:  monthString = "February";
                        break;
                    case 3:  monthString = "March";
                        break;
                    case 4:  monthString = "April";
                        break;
                    case 5:  monthString = "May";
                        break;
                    case 6:  monthString = "June";
                        break;
                    case 7:  monthString = "July";
                        break;
                    case 8:  monthString = "August";
                        break;
                    case 9:  monthString = "September";
                        break;
                    case 10: monthString = "October";
                        break;
                    case 11: monthString = "November";
                        break;
                    case 12: monthString = "December";
                        break;
                    default: monthString = "Invalid month";
                        break;
                }
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteEvent(searchDate);
                    }
                });
                Button btn = (Button)findViewById(R.id.addBtn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(CalendarActivity.this, AddEvent.class));
                    }
                });
                Button btnB = (Button)findViewById(R.id.addBtn);

                btnB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(CalendarActivity.this,CalendarActivity.class));
                    }
                });
                Button btnS = (Button)findViewById(R.id.buttonS);

                btnS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(CalendarActivity.this, SearchActivity.class));
                    }
                });
                String date = i2 + "/" + monthString + "/" + i;

                myDate.setText(date);
                eventText.setText(findEvent(searchDate));
            }
        });
    }
    public void sendMessage(View view){
        Intent intent=new Intent(this,AddEvent.class);
        startActivity(intent);
    }
    public void sendSearch(View view){
        Intent intent=new Intent(this,SearchActivity.class);
        startActivity(intent);
    }
    public void sendUpdate(View view){
        Intent intent=new Intent(this,UpdateActivity.class);
        intent.putExtra("key",searchDate);
        startActivity(intent);
    }

    private String findEvent(final String kelime){
        db = "http://marvelous-wind-cave-84354.herokuapp.com/api/v1/rendezvous_data.json";
        stringRequest = new StringRequest(Request.Method.GET, db, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result = "";
                int size=0;
                try {
                    JSONArray appointments = new JSONArray(response);
                    for(int i = 0; i < appointments.length(); i++) {
                        JSONObject appointment = (JSONObject) appointments.get(i);
                        start_date = appointment.getString("start_date");
                        if (start_date.equalsIgnoreCase(kelime)) {
                            title = appointment.getString("title");
                            text = appointment.getString("text");
                            start_date = appointment.getString("start_date");
                            start_time = appointment.getString("start_time");
                            end_time = appointment.getString("end_time");
                            end_date = appointment.getString("end_date");
                            size ++;
                            result += "Title: " + title + "\n" + "Text: " + text + "\n" + "Start date: " + start_date +  "\n" + "   Start time: " + start_time + "\n" + "End Date: " + end_date +  "\n"+"   End time: " + end_time + "\n" + "-------------\n";
                        }
                    }
                    if (size==0) {
                        result = "Etkinlik bulunamadı";
                        updateButton.setVisibility(View.INVISIBLE);
                        deleteButton.setVisibility(View.INVISIBLE);
                    }
                    else{
                        updateButton.setVisibility(View.VISIBLE);
                        deleteButton.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                   result =("Error Json!");
                }
                //tv.setText(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError)
                    result = (( "1"));
            }
        });
        queue.add(stringRequest);
        return result;
    }


    private void deleteEvent(final String searchDate){
        db = "http://marvelous-wind-cave-84354.herokuapp.com/api/v1/rendezvous_data.json";
        stringRequest = new StringRequest(Request.Method.DELETE, db, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result = "";
                int size=0;
                try {
                    JSONArray appointments = new JSONArray(response);
                    for(int i = 0; i < appointments.length(); i++) {
                        JSONObject appointment = (JSONObject) appointments.get(i);
                        start_date = appointment.getString("start_date");
                        if (start_date.equalsIgnoreCase(searchDate)) {
                            title = appointment.getString("title");
                            text = appointment.getString("text");
                            start_date = appointment.getString("start_date");
                            start_time = appointment.getString("start_time");
                            end_time = appointment.getString("end_time");
                            end_date = appointment.getString("end_date");
                            size ++;
                            result += "Title: " + title + "\n" + "Text: " + text + "\n" + "Start date: " + start_date +  "\n" + "   Start time: " + start_time + "\n" + "End Date: " + end_date +  "\n"+"   End time: " + end_time + "\n" + "-------------\n";
                        }
                    }
                } catch (JSONException e) {
                    result =("Error Json!");
                }
                //tv.setText(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError)
                    result = (( "1"));
            }
        });
        queue.add(stringRequest);
    }

}