package com.example.musta.calender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AgainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_again);
    }
    public void goToAdd(View view){
        Intent intent=new Intent(this,AddEvent.class);
        startActivity(intent);
    }
}
