package com.boomsquad.luggerztest;

import android.widget.EditText;

public class Lugs {

    private String date, time, items, itemLocation, destination;

    public Lugs(String date, String time, String items, String itemLocation, String destination) {
        this.date = date;
        this.time = time;
        this.items = items;
        this.itemLocation = itemLocation;
        this.destination = destination;
    }

    public Lugs() {
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getItems() {
        return items;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
