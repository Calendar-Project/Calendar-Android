package com.example.musta.calender;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class UpdateActivity extends AppCompatActivity {
    String title,text,start_date,start_time,end_time,end_date,value;
    String db = "http://marvelous-wind-cave-84354.herokuapp.com/api/v1/rendezvous_data.json";
    EditText editText1,editText2,editText3,editText4,editText5,editText6;
    Button update;
    RequestQueue queue;
    public ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        update=findViewById(R.id.update);
        editText1 = findViewById(R.id.baslık);
        editText2 = findViewById(R.id.acıklama);
        editText3 = findViewById(R.id.basTar);
        editText4 = findViewById(R.id.basSaat);
        editText5 = findViewById(R.id.BitisTar);
        editText6 = findViewById(R.id.BitisSaat);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("key");
        }
        queue = Volley.newRequestQueue(this);
        search();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    executePutMethod();

                    }
        });
    }

    private void executePutMethod() {


        progress = ProgressDialog.show(UpdateActivity.this,"",
                "Lütfen Bekleyiniz", true);


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title",editText1.getText().toString());
            jsonObject.put("text",editText2.getText().toString());
            jsonObject.put("start_date",editText3.getText().toString());
            jsonObject.put("start_time",editText4.getText().toString());
            jsonObject.put("end_time",editText5.getText().toString());
            jsonObject.put("end_date",editText6.getText().toString());
        } catch (JSONException e) {
            // handle exception
        }


        JsonObjectRequest jsonForPutRequest = new JsonObjectRequest(
                Request.Method.PUT,db,jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progress.dismiss();
                        Log.i("log",response.toString());
                        try {
                            Toast.makeText(getApplicationContext(),""+response.getString("mesaj"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                progress.dismiss();

                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    JSONObject jsonObject = null;
                    String errorMessage = null;

                    switch(response.statusCode){
                        case 400:
                            errorMessage = new String(response.data);

                            try {

                                jsonObject = new JSONObject(errorMessage);
                                String serverResponseMessage =  (String)jsonObject.get("hataMesaj");
                                Toast.makeText(getApplicationContext(),""+serverResponseMessage,Toast.LENGTH_LONG).show();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
                }
            }


        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> param = new HashMap<String, String>();

                return param;
            }


        };

        jsonForPutRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonForPutRequest);
        //AppController.getInstance().addToRequestQueue(jsonForPutRequest);


    }

    private void search(){
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, db, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray appointments = new JSONArray(response);
                    for(int i = 0; i < appointments.length(); i++) {
                        JSONObject appointment = (JSONObject) appointments.get(i);
                        if (value.equalsIgnoreCase(appointment.getString("start_date"))) {
                            title = appointment.getString("title");
                            text = appointment.getString("text");
                            start_date = appointment.getString("start_date");
                            start_time = appointment.getString("start_time");
                            end_time = appointment.getString("end_time");
                            end_date = appointment.getString("end_date");
                            editText1.setText(title);
                            editText2.setText(text);
                            editText3.setText(start_date);
                            editText4.setText(start_time);
                            editText6.setText(end_time);
                            editText5.setText(end_date);

                        }
                    }

                } catch (JSONException e) {

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
      //  queue.add(stringRequest);
       AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
