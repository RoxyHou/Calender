package com.example.calender;

import static com.example.calender.CalenderUtils.daysInMonthArray;
import static com.example.calender.CalenderUtils.daysInWeekArray;
import static com.example.calender.CalenderUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class WeekViewActivity extends AppCompatActivity implements CalenderAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calenderRecyclerView;
    private ListView eventListView;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        initWidgets();
        setWeekView();
    }

    private void setWeekView() {
        monthYearText.setText(monthYearFromDate(CalenderUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalenderUtils.selectedDate);

        CalenderAdapter calenderAdapter = new CalenderAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),7);
        calenderRecyclerView.setLayoutManager(layoutManager);
        calenderRecyclerView.setAdapter(calenderAdapter);
        setEventAdapter();
    }




    private void initWidgets() {
        calenderRecyclerView = findViewById(R.id.calenderRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    public void previousWeekAction(View view) {
        CalenderUtils.selectedDate = CalenderUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view) {
        CalenderUtils.selectedDate = CalenderUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {

        CalenderUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter() {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalenderUtils.selectedDate);
        Collections.sort(dailyEvents);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void newEventAction(View view) {
        startActivity(new Intent(this, EventEditActivity.class));
    }
}