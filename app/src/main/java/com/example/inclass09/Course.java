package com.example.inclass09;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    String courseNumber;

    @ColumnInfo
    String courseName;

    @ColumnInfo
    int creditHours;

    @ColumnInfo
    char courseGrade;

    public Course(String courseNumber, String courseName, int creditHours, char courseGrade) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.creditHours = creditHours;
        this.courseGrade = courseGrade;
    }

    public Course() {
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseNumber='" + courseNumber + '\'' +
                ", courseName='" + courseName + '\'' +
                ", creditHours=" + creditHours +
                ", courseGrade=" + courseGrade +
                '}';
    }
}
