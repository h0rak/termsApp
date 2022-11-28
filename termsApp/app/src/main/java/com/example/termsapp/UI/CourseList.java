package com.example.termsapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.termsapp.Entity.Term;
import com.example.termsapp.R;

public class CourseList extends AppCompatActivity {

    //supporting code here
    // example : id, name, price, product ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        // some code here imported from the course list activity layout

        // make sure that I create the repository object
    }

    // save button comes from the course list activity layout
    public void saveButton(View view){
        Term term;
//        if (termID == -1) {
//
//        }
    }
}