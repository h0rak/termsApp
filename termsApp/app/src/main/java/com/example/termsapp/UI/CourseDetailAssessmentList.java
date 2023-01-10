package com.example.termsapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.termsapp.Database.Repository;
import com.example.termsapp.Entity.Course;
import com.example.termsapp.R;

import java.util.ArrayList;

public class CourseDetailAssessmentList extends AppCompatActivity {

    int cID;
    String cName;
    String cStart;
    String cEnd;
    String cStatus;
    String iName;
    String iPhone;
    String iEmail;
    String oNote;
    int tID;
    EditText cNameEdit;
    EditText cStartEdit;
    EditText cEndEdit;
    Spinner cStatusSpinner;
    EditText iNameEdit;
    EditText iPhoneEdit;
    EditText iEmailEdit;
    EditText oNoteEdit;
    Repository repository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail_assessment_list);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cID = getIntent().getIntExtra("id", -1);
        cName = getIntent().getStringExtra("name");
        cStart = getIntent().getStringExtra("start");
        cEnd = getIntent().getStringExtra("end");
        cStatusSpinner = findViewById(R.id.courseStatusSpinner);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,R.array.status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cStatusSpinner.setAdapter(statusAdapter);
        cStatusSpinner.setSelection(0);
        cStatus = cStatusSpinner.getSelectedItem().toString();
        iName = getIntent().getStringExtra("iName");
        iPhone = getIntent().getStringExtra("iPhone");
        iEmail = getIntent().getStringExtra("iEmail");
        oNote = getIntent().getStringExtra("oNote");
        tID = getIntent().getIntExtra("tID", -1);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursedetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.saveCourse:
                Course course;
                repository = new Repository(getApplication());
                if (cID == -1) {
                    if (repository.getAllCourses().size() == 0)
                        cID = 1;
                    else
                        cID = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseID() + 1;
                    course = new Course(cID, cNameEdit.getText().toString(), cStartEdit.getText().toString(), cEndEdit.getText().toString(), cStatus, iNameEdit.getText().toString(), iPhoneEdit.getText().toString(), iEmailEdit.getText().toString(), oNoteEdit.getText().toString(), tID);
                    repository.insert(course);
                    Toast.makeText(this, "Course Saved Successfully", Toast.LENGTH_LONG).show();
                }
                else {
                    course = new Course(cID, cNameEdit.getText().toString(), cStartEdit.getText().toString(), cEndEdit.getText().toString(), cStatus, iNameEdit.getText().toString(), iPhoneEdit.getText().toString(), iEmailEdit.getText().toString(), oNoteEdit.getText().toString(), tID);
                    repository.update(course);
                    Toast.makeText(this, "Course Updated Successfully", Toast.LENGTH_LONG).show();
                }
                this.finish();
                return true;

            case R.id.deleteCourse:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}