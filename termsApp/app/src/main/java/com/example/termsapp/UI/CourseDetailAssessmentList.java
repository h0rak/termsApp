package com.example.termsapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.termsapp.Database.Repository;
import com.example.termsapp.Entity.Assessment;
import com.example.termsapp.Entity.Course;
import com.example.termsapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repository = new Repository(getApplication());
        cID = getIntent().getIntExtra("id", -1);

        cNameEdit = findViewById(R.id.courseTitleEditText);
        cName = getIntent().getStringExtra("name");
        cNameEdit.setText(cName);

        cStartEdit = findViewById(R.id.courseStartEditText);
        cStart = getIntent().getStringExtra("start");
        cStartEdit.setText(cStart);

        cEndEdit = findViewById(R.id.courseEndEditText);
        cEnd = getIntent().getStringExtra("end");
        cEndEdit.setText(cEnd);

        cStatusSpinner = findViewById(R.id.courseStatusSpinner);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cStatusSpinner.setAdapter(statusAdapter);
        cStatusSpinner.setSelection(0);
        cStatus = cStatusSpinner.getSelectedItem().toString();

        iNameEdit = findViewById(R.id.courseInstNameEditText);
        iName = getIntent().getStringExtra("iName");
        iNameEdit.setText(iName);

        iPhoneEdit = findViewById(R.id.courseInstPhoneEditText);
        iPhone = getIntent().getStringExtra("iPhone");
        iPhoneEdit.setText(iPhone);

        iEmailEdit = findViewById(R.id.courseInstEmailEditText);
        iEmail = getIntent().getStringExtra("iEmail");
        iEmailEdit.setText(iEmail);

        oNoteEdit = findViewById(R.id.courseNoteEditText);
        oNote = getIntent().getStringExtra("oNote");
        oNoteEdit.setText(oNote);

        tID = getIntent().getIntExtra("termID", -1);

        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getAllAssessments()) {
            if (a.getCourseID() == cID) filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessments(filteredAssessments);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton3);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetailAssessmentList.this, AssessmentDetail.class);
                intent.putExtra("cID", cID);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursedetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
                    course = new Course(cID, cNameEdit.getText().toString(), cStartEdit.getText().toString(), cEndEdit.getText().toString(), cStatusSpinner.getSelectedItem().toString(), iNameEdit.getText().toString(), iPhoneEdit.getText().toString(), iEmailEdit.getText().toString(), oNoteEdit.getText().toString(), tID);
                    repository.insert(course);
                    Toast.makeText(this, "Course Saved Successfully", Toast.LENGTH_LONG).show();
                } else {
                    course = new Course(cID, cNameEdit.getText().toString(), cStartEdit.getText().toString(), cEndEdit.getText().toString(), cStatusSpinner.getSelectedItem().toString(), iNameEdit.getText().toString(), iPhoneEdit.getText().toString(), iEmailEdit.getText().toString(), oNoteEdit.getText().toString(), tID);
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