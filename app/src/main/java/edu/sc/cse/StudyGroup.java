package edu.sc.cse;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jpvil on 2/12/2017. Class to tets wether data it is store and can be read from fire base
 */

public class StudyGroup {
    public String date;
    public String description;
    public String location;
    List<String> members = new ArrayList<>();
    public String time;
    public String course;
    public String host;
    public String eventid;

    public StudyGroup(){

    }
    public StudyGroup(String eventid,String date, String description, String location, List <String> members,String time, String course, String host) {
        this.date =date;
        this.eventid = eventid;
        this.description = description;
        this.location = location;
        this.members = members;
        this.time = time;
        this.course = course;
        this.host = host;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }


    public String getLocation() {
        return location;
    }

    public List<String> getMembers() {
        return members;
    }

    public String getTime() {
        return time;
    }

    public String getCourse() {
        return course;
    }

    public String getHost() {
        return host;
    }
    public String getEventid() {
        return eventid;
    }
    public void setEventid(String eventid) {
        this.eventid = eventid;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
