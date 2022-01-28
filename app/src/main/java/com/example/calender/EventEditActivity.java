package com.example.calender;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.channels.CancelledKeyException;
import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity {

    private EditText eventNameET, eventTimeETH, eventTimeETM;
    private TextView eventDateTV, eventTimeTV;

    private LocalTime time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Date: " + CalenderUtils.formattedDate(CalenderUtils.selectedDate));
        eventTimeETH.setText(String.valueOf(time.getHour()));
        eventTimeETM.setText(String.valueOf(time.getMinute()));
    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
        eventTimeETH = findViewById(R.id.eventTimeETH);
        eventTimeETM = findViewById(R.id.eventTimeETM);


    }
    public void saveEventAction(View view) {
        String eventName = eventNameET.getText().toString();
        int hour = Integer.valueOf(eventTimeETH.getText().toString());
        int minute = Integer.valueOf(eventTimeETM.getText().toString());
        Event newEvent = new Event(eventName, CalenderUtils.selectedDate, LocalTime.of(hour, minute));
        Event.eventsList.add(newEvent);
        finish();
    }
}