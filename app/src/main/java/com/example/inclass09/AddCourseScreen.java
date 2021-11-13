package com.example.inclass09;

import static com.example.inclass09.MainActivity.COURSE_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AddCourseScreen extends AppCompatActivity {

    TextView editTextCourseNumber, editTextCourseName, editTextCreditHours;
    RadioGroup radioGroup;

    char courseGrade = 'E';
    double courseGradePoints = -1.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_screen);
        setTitle(getString(R.string.add_course));

        // Assign layout items
        editTextCourseNumber = findViewById(R.id.editTextCourseNumber);
        editTextCourseName = findViewById(R.id.editTextCourseName);
        editTextCreditHours = findViewById(R.id.editTextCreditHours);
        radioGroup = findViewById(R.id.radioGroup);

        // Listener for radio group
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.buttonA) {
                    courseGrade = 'A';
                    courseGradePoints = 4.0;
                } else if (i == R.id.buttonB) {
                    courseGrade = 'B';
                    courseGradePoints = 3.0;
                } else if (i == R.id.buttonC) {
                    courseGrade = 'C';
                    courseGradePoints = 2.0;
                } else if (i == R.id.buttonD) {
                    courseGrade = 'D';
                    courseGradePoints = 1.0;
                } else if (i == R.id.buttonF) {
                    courseGrade = 'F';
                    courseGradePoints = 0.0;
                }
            }
        });

        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseNumber = editTextCourseNumber.getText().toString();
                String courseName = editTextCourseName.getText().toString();
                int courseCreditHours = Integer.parseInt(editTextCreditHours.getText().toString());
                if (courseNumber.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.invalid_course_number), Toast.LENGTH_SHORT).show();
                } else if (courseName.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.invalid_course_name), Toast.LENGTH_SHORT).show();
                } else if (courseCreditHours == 0) {
                    Toast.makeText(getApplicationContext(), getString(R.string.invalid_credit_hours), Toast.LENGTH_SHORT).show();
                } else if (courseGradePoints < 0) {
                    Toast.makeText(getApplicationContext(), getString(R.string.select_course_grade), Toast.LENGTH_SHORT).show();
                } else {
                    Course course = new Course(courseNumber, courseName, courseCreditHours, courseGrade, courseGradePoints * courseCreditHours);
                    Intent intent = new Intent(AddCourseScreen.this, MainActivity.class);
                    intent.putExtra(COURSE_KEY, course);
                    startActivity(intent);
                }
            }
        });

        findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCourseScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}