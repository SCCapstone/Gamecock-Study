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
    List<String> members = new ArrayList<String>();
    public String time;
    public String course;
    public String host;

    public StudyGroup(){

    }
    public StudyGroup(String date, String description, String location, List <String> members,String time, String course, String host) {
        this.date =date;
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
}
