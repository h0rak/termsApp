package com.example.termsapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.termsapp.DAO.AssessmentDAO;
import com.example.termsapp.DAO.CourseDAO;
import com.example.termsapp.DAO.TermDAO;
import com.example.termsapp.Entity.Assessment;
import com.example.termsapp.Entity.Course;
import com.example.termsapp.Entity.Term;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 6, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {

    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "myDatabaseBuilder.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
