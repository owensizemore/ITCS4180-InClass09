/*
    InClass 09
    AppDatabase.java
    Owen Sizemore
    Dylan Goodman
 */

package com.example.inclass09;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CourseDAO courseDAO();
}
