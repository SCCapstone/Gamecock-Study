package edu.sc.cse;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jpvil on 2/12/2017. Class to tets wether data it is store and can be read from fire base
 */

public class StudyGroup {
    public String date;
    public String description;
    public String user;
    public String location;
    public String members;
    public String Time;


    public StudyGroup(String date, String description, String user, String location, String members,String time) {
        // ...
    }

}
//
//    // Get a reference to our posts
//    final FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference ref = database.getReference("https://gamecockstudy-5e0b5.firebaseio.com/");
//
//// Attach a listener to read the data at our posts reference
//
//ref.addValueEventListener(new ValueEventListener() {
//@Override
//public void onDataChange(DataSnapshot dataSnapshot) {
//        Post post = dataSnapshot.getValue(Post.class);
//        System.out.println(post);
//        }
//
//@Override
//public void onCancelled(DatabaseError databaseError) {
//        System.out.println("The read failed: " + databaseError.getCode());
//        }
//        });
