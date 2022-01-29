package com.example.calender;

import com.Google.firebase.firestore.DocumentID;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event implements Comparable<Event> {

    public static ArrayList<Event> eventsList = new ArrayList<>();

    public static ArrayList<Event> eventsForDate(LocalDate date) {
        ArrayList<Event> events = new ArrayList<>();
        for (Event event : eventsList) {
            if (event.getDate().equals(date)) {
                events.add(event);
            }
        }

        return events;
    }

    @DocumentID 
    private String id;
    private String name;
    private LocalDate date;
    private LocalTime time;
    

    public Event(String name, LocalDate date, LocalTime time, String id) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int compareTo(Event o) {
        return this.time.compareTo(o.time);
    }
}
