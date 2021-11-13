/*
    InClass 09
    MainActivity.java
    Owen Sizemore
    Dylan Goodman
 */

package com.example.inclass09;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CoursesRecyclerViewAdapter.ICoursesRecyclerViewAdapterInterface {

    TextView gpaTextView, hoursTextView;

    final static public String COURSE_KEY = "COURSE_KEY";

    RecyclerView coursesRecyclerView;
    LinearLayoutManager coursesLayoutManager;
    CoursesRecyclerViewAdapter coursesRecyclerViewAdapter;

    List<Course> courses = new ArrayList<>();

    int totalHours;
    double totalGradePoints;

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
        courses = db.courseDAO().getAll();

        //setting recyclerView up and passing it the courses list
        coursesRecyclerView = findViewById(R.id.coursesRecyclerView);
        coursesRecyclerView.setHasFixedSize(true);
        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);
        coursesRecyclerViewAdapter = new CoursesRecyclerViewAdapter(courses, this);
        coursesRecyclerView.setAdapter(coursesRecyclerViewAdapter);

        setLabels();

        // using button to switch between screens
        findViewById(R.id.toAddCourseBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCourseScreen.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();


        return true;
    }

    public void setLabels() {
        gpaTextView.setText(getString(R.string.gpa));
        hoursTextView.setText(getString(R.string.hours));
        if (!courses.isEmpty()) {
            // calculate GPA and total credit hours
            totalHours = 0;
            totalGradePoints = 0;
            for (Course c: courses) {
                totalHours += c.creditHours;
                totalGradePoints += c.courseGradePoints;
            }
            double gpa = Math.round((totalGradePoints / totalHours) * 100.0) / 100.0;

            gpaTextView.setText(gpaTextView.getText() + " " + String.valueOf(gpa));
            hoursTextView.setText(hoursTextView.getText() + " " + String.valueOf(totalHours));
        }else{
            gpaTextView.setText(R.string.gpa4);
            hoursTextView.setText(R.string.hour0);
        }
    }

    @Override
    public void deleteCourse(Course course, int position) {
        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "course.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        //deletes course from database
        db.courseDAO().delete(course);

        // remove from list
        courses.remove(course);

        coursesRecyclerViewAdapter.notifyItemRemoved(position);
        setLabels();
    }
}