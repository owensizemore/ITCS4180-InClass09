/*
    InClass 09
    Course.java
    Owen Sizemore
    Dylan Goodman
 */
package com.example.inclass09;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "courses")
public class Course implements Serializable {
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

    @ColumnInfo
    double courseGradePoints;

    public Course(String courseNumber, String courseName, int creditHours, char courseGrade, double courseGradePoints) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.creditHours = creditHours;
        this.courseGrade = courseGrade;
        this.courseGradePoints = courseGradePoints;
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
                ", courseGradePoints=" + courseGradePoints +
                '}';
    }
}
