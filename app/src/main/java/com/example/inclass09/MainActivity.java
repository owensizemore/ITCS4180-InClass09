package com.example.inclass09;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView coursesRecyclerView;
    LinearLayoutManager coursesLayoutManager;
    CoursesRecyclerViewAdapter coursesRecyclerViewAdapter;

    //another comment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.grades));

        //creates a new room database db
        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "course.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        //getting database DAO and all entries in database
        CourseDAO courseDAO = db.courseDAO();
        List<Course> courses = courseDAO.getAll();

        //setting recyclerView up and passing it the courses list
        coursesRecyclerView = findViewById(R.id.coursesRecyclerView);
        coursesRecyclerView.setHasFixedSize(true);
        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);
        coursesRecyclerViewAdapter = new CoursesRecyclerViewAdapter(courses);
        coursesRecyclerView.setAdapter(coursesRecyclerViewAdapter);


        //testing for database
        /*
        db.courseDAO().insertAll(new Course("ITCS 4180", "Mobile App Development", 3, 'A'));
        Log.d("TAG", "OnCreate: " + db.courseDAO().getAll());
        */


        //todo remove temporary button and add screen move to title bar
        //using temp button to switch between screens
        findViewById(R.id.toAddCourseBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCourseScreen.class);
                startActivity(intent);
            }
        });
    }
}