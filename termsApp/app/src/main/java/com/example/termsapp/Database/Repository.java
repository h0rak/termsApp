package com.example.termsapp.Database;

import android.app.Application;
import android.widget.Adapter;

import com.example.termsapp.DAO.AssessmentDAO;
import com.example.termsapp.DAO.CourseDAO;
import com.example.termsapp.DAO.TermDAO;
import com.example.termsapp.Entity.Assessment;
import com.example.termsapp.Entity.Course;
import com.example.termsapp.Entity.Term;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private final TermDAO mTermDAO;
    private final CourseDAO mCourseDAO;
    private final AssessmentDAO mAssessmentDAO;
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssessments;
    private static final int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
    }

    public List<Term> getAllTerms(){
        mAllTerms=mTermDAO.getAllTerms();
        return mAllTerms;
    }

    public List<Course> getAllCourses(){
        mAllCourses=mCourseDAO.getAllCourses();
        return mAllCourses;
    }

    public List<Assessment> getAllAssessments(){
        mAllAssessments=mAssessmentDAO.getAllAssessments();
        return mAllAssessments;
    }

    public void insert(Term term){
        mTermDAO.insert(term);
    }

    public void insert(Course course){
        mCourseDAO.insert(course);
    }

    public void delete(Term term){
        mTermDAO.delete(term);
    }

    public void delete(Course course){
        mCourseDAO.delete(course);
    }

    public void update(Term term) {
      mTermDAO.update(term);
    }

    public void update(Course course) {
        mCourseDAO.update(course);
    }

    /*public List<Course> getCoursesByTermID(int termID) {
            // this nee
    }*/
}
