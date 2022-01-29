package com.example.calender;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.channels.CancelledKeyException;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EventEditActivity extends AppCompatActivity {

    private EditText eventNameET, eventTimeETH, eventTimeETM;
    private TextView eventDateTV, eventTimeTV;

    private LocalTime time;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Date: " + CalenderUtils.formattedDate(CalenderUtils.selectedDate));
        eventTimeETH.setText(String.valueOf(time.getHour()));
        eventTimeETM.setText(String.valueOf(time.getMinute()));

        db = FirebaseFirestore.getInstance();

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
        
        Event newEvent = new Event(eventName, CalenderUtils.selectedDate, LocalTime.of(hour, minute), null);
        Event.eventsList.add(newEvent);

        //store to db
        Map<String, Object> event = new HashMap<>();
        event.put("name", eventName);
        event.put("date", CalenderUtils.selectedDate.toString());
        event.put("time", String.valueOf(hour) + ":" + String.valueOf(minute));
        // Add a new document with a generated ID
        db.collection("Calender").add(event).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                //Toast.makeText(EventEditActivity.this, "Your Course has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();

                newEvent.setId(documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                //Toast.makeText(EventEditActivity.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
            }
        });

        finish();
    }
}