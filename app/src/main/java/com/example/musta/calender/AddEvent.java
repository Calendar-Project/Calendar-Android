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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddEvent extends AppCompatActivity {

    String db;
    EditText editText1,editText2,editText3,editText4,editText5,editText6;
    Button add;
    public ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        add=findViewById(R.id.ekle);
        editText1 = findViewById(R.id.baslık);
        editText2 = findViewById(R.id.acıklama);
        editText3 = findViewById(R.id.basTar);
        editText4 = findViewById(R.id.basSaat);
        editText5 = findViewById(R.id.BitisTar);
        editText6 = findViewById(R.id.BitisSaat);
        db = "http://marvelous-wind-cave-84354.herokuapp.com/api/v1/rendezvous_data.json";
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progress = ProgressDialog.show(AddEvent.this,"",
                        "Lütfen Bekleyiniz", true);



                StringRequest jsonForPostRequest = new StringRequest(
                        Request.Method.POST,db,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                progress.dismiss();
                                Log.i("log",response.toString());

                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    Toast.makeText(getApplicationContext(),""+jsonObject.getString("mesaj"),Toast.LENGTH_LONG).show();
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
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();

                        params.put("title",editText1.getText().toString());
                        params.put("text",editText2.getText().toString());
                        params.put("start_date",editText3.getText().toString());
                        params.put("start_time",editText4.getText().toString());
                        params.put("end_time",editText6.getText().toString());
                        params.put("end_date",editText5.getText().toString());


                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {

                        Map<String, String> param = new HashMap<String, String>();

                        return param;
                    }


                };

                jsonForPostRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                AppController.getInstance().addToRequestQueue(jsonForPostRequest);


            }
        });
    }
    public void goToAgain(View view){
        Intent intent=new Intent(this,AgainActivity.class);
        startActivity(intent);
        }

}
