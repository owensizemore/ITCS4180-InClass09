package com.example.inclass09;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDAO {

    @Query("SELECT * FROM courses")
    List<Course> getAll();

    @Query("SELECT * FROM courses WHERE id = :id")
    Course findById(long id);

    @Update
    void update(Course course);

    @Insert
    void insertAll(Course... courses);

    @Delete
    void delete(Course course);
}
