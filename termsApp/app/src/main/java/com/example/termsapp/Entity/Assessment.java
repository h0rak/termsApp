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


    public Assessment(int assessmentID, Boolean isPerformanceBased, String assessmentStart, String assessmentEnd) {
        this.assessmentID = assessmentID;
        this.isPerformanceBased = isPerformanceBased;
        this.assessmentStart = assessmentStart;
        this.assessmentEnd = assessmentEnd;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public Boolean getPerformanceBased() {
        return isPerformanceBased;
    }

    public void setPerformanceBased(Boolean performanceBased) {
        isPerformanceBased = performanceBased;
    }

    public String getAssessmentStart() {
        return assessmentStart;
    }

    public void setAssessmentStart(String assessmentStart) {
        this.assessmentStart = assessmentStart;
    }

    public String getAssessmentEnd() {
        return assessmentEnd;
    }

    public void setAssessmentEnd(String assessmentEnd) {
        this.assessmentEnd = assessmentEnd;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "assessmentID=" + assessmentID +
                ", isPerformanceBased=" + isPerformanceBased +
                ", assessmentStart='" + assessmentStart + '\'' +
                ", assessmentEnd='" + assessmentEnd + '\'' +
                '}';
    }
}
