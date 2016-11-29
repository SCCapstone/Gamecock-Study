package com.example.helloworld;

/**
 * Created by After-Prom on 11/29/2016.
 */

public class Group {
    private String Location;
    private String time;
    private String date;
    private String description;

    public Group() {
      /*Blank default constructor essential for Firebase*/
    }
    //Getters and setters
    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
