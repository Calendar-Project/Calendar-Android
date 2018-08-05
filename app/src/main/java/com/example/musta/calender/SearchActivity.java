package com.example.musta.calender;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class SearchActivity extends AppCompatActivity {

    Button buttonS;
    String kelime="";
    TextView textView;
    EditText editText;
    RequestQueue queue;
    String db, result,title,text,start_date,start_time,end_time,end_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        textView = findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        editText = findViewById(R.id.kelime);
        buttonS = findViewById(R.id.buttonS);
        String keyword = intent.getStringExtra(CalendarActivity.EXTRA_MESSAGE);
        textView.setText(keyword);

        queue = Volley.newRequestQueue(this);
        buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kelime=editText.getText().toString();
                kelime=kelime.toLowerCase();
                search(kelime);
            }
        });
    }

    private void search(final String kelime){
        db = "http://marvelous-wind-cave-84354.herokuapp.com/api/v1/rendezvous_data.json";
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, db, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result = "Bulunan etkinlikler : \n ----------- \n";
                int size=0;
                try {
                    JSONArray appointments = new JSONArray(response);
                    for(int i = 0; i < appointments.length(); i++) {
                        JSONObject appointment = (JSONObject) appointments.get(i);
                        title = appointment.getString("title");
                        if (title.equalsIgnoreCase(kelime)) {
                            text = appointment.getString("text");
                            start_date = appointment.getString("start_date");
                            start_time = appointment.getString("start_time");
                            end_time = appointment.getString("end_time");
                            end_date = appointment.getString("end_date");
                            size ++;
                            result += "Title: " + title + "\n" + "Text: " + text + "\n" + "Start date: " + start_date + "   Start time: " + start_time + "\n" + "End Date: " + end_date + "   End time: " + end_time + "\n" + "-------------\n";
                        }
                    }
                    if (size==0)
                        result="Etkinlik bulunamadı";
                } catch (JSONException e) {
                    textView.setText("Error Json!");
                }
                textView.setText(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError)
                    textView.setText(( "1"));
                else if  (error instanceof AuthFailureError)
                    textView.setText(( "2"));
                else if  (error instanceof ServerError)
                    textView.setText(( "3"));
                else if  (error instanceof NetworkError)
                    textView.setText(( "4"));
                else if  (error instanceof ParseError)
                    textView.setText(( "5"));
              //  textView.setText("Error Volley!");
            }
        });
        queue.add(stringRequest);
    }
}