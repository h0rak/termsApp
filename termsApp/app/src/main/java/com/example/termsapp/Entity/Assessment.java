package com.example.termsapp.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private Boolean isPerformanceBased;
    private String assessmentStart;
    private String assessmentEnd;
    // private TriggerEventListener??
}
