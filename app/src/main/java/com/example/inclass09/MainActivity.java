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
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView gpaTextView, hoursTextView;

    final static public String COURSE_KEY = "COURSE_KEY";

    RecyclerView coursesRecyclerView;
    LinearLayoutManager coursesLayoutManager;
    CoursesRecyclerViewAdapter coursesRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.grades));

        gpaTextView = findViewById(R.id.gpaTextView);
        hoursTextView = findViewById(R.id.hoursTextView);

        //creates a new room database db
        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "course.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        // if a course was received through an extra, add to database
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(COURSE_KEY)) {
            Course course = (Course) getIntent().getSerializableExtra(COURSE_KEY);
            db.courseDAO().insertAll(course);
        }

        //getting database DAO and all entries in database
        List<Course> courses = db.courseDAO().getAll();

        // calculate GPA and total credit hours
        int totalHours = 0;
        double totalGradePoints = 0;
        for (Course c: courses) {
            totalHours += c.creditHours;
            totalGradePoints += c.courseGradePoints;
        }
        // TODO round this number
        double gpa = totalGradePoints/totalHours;

        gpaTextView.setText(getString(R.string.gpa) + " " + String.valueOf(gpa));
        hoursTextView.setText(getString(R.string.hours) + " " + String.valueOf(totalHours));

        //setting recyclerView up and passing it the courses list
        coursesRecyclerView = findViewById(R.id.coursesRecyclerView);
        coursesRecyclerView.setHasFixedSize(true);
        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);
        coursesRecyclerViewAdapter = new CoursesRecyclerViewAdapter(courses);
        coursesRecyclerView.setAdapter(coursesRecyclerViewAdapter);

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