package com.example.musta.calender;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView myDate;
    LinearLayout myEvent;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        btn = (Button) findViewById(R.id.addBtn);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        myDate = (TextView) findViewById(R.id.myDate);
        myEvent = (LinearLayout) findViewById(R.id.myEvent);
     //   btn.on
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String monthString;
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
      //          Toast.makeText(getApplicationContext(),i2 +"/"+ i1 + "/" + i, Toast.LENGTH_LONG).show();
                String date = i2 + "/" + monthString + "/" + i;

                myDate.setText(date);
                if(myEvent.getVisibility() == View.INVISIBLE) {
                    myEvent.setVisibility(View.VISIBLE);
                }else if (myEvent.getVisibility() == View.VISIBLE)
                    myEvent.setVisibility(View.INVISIBLE);
            //    Toast.makeText(getApplicationContext(), i2 + "/" + i1 + "/" + i, Toast.LENGTH_LONG).show();
;
            }
        });
    }
}
