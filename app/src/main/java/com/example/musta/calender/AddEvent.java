package com.example.musta.calender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddEvent extends AppCompatActivity {

    String title,text,start_date,start_time,end_time,end_date;
    EditText editText1,editText2,editText3,editText4,editText5,editText6;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Intent intent = getIntent();

        editText1 = findViewById(R.id.baslık);
        editText2 = findViewById(R.id.acıklama);
        editText3 = findViewById(R.id.basTar);
        editText4 = findViewById(R.id.basSaat);
        editText5 = findViewById(R.id.BitisTar);
        editText6 = findViewById(R.id.BitisSaat);


    }
    public void goToAgain(View view){
        Intent intent=new Intent(this,AgainActivity.class);
        startActivity(intent);
    }
    public void backMessage(View view){
        title=editText1.getText().toString();
        text=editText2.getText().toString();
        start_date = editText3.getText().toString();
        start_time=editText4.getText().toString();
        end_date=editText5.getText().toString();
        end_time=editText6.getText().toString();
        System.out.println(title+" "+text+" "+start_date+" "+start_time+" "+end_date+" "+end_time);
        // add(kelime);
        Intent intent=new Intent(this,CalendarActivity.class);
        startActivity(intent);
    }
}

