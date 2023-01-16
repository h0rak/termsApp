package com.example.termsapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.termsapp.Database.Repository;
import com.example.termsapp.Entity.Assessment;
import com.example.termsapp.Entity.Course;
import com.example.termsapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();


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

        // testing for datePicker below
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        String currentDate = sdf.format(new Date());
        // testing or datePicker above

        cStartEdit = findViewById(R.id.courseStartEditText);
        cStart = getIntent().getStringExtra("start");
        cStartEdit.setText(cStart);

        cEndEdit = findViewById(R.id.courseEndEditText);
        cEnd = getIntent().getStringExtra("end");
        cEndEdit.setText(cEnd);

        cStartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateToString = cStartEdit.getText().toString();
                try {
                    startCalendar.setTime(sdf.parse(dateToString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetailAssessmentList.this, startDate, startCalendar.get(Calendar.YEAR),
                        startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, month);
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStartLabel();
            }
        };

        cEndEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateToString = cStartEdit.getText().toString();
                try {
                    endCalendar.setTime(sdf.parse(dateToString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetailAssessmentList.this, endDate, endCalendar.get(Calendar.YEAR),
                        endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                endCalendar.set(Calendar.YEAR, year);
                endCalendar.set(Calendar.MONTH, month);
                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndLabel();
            }
        };

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

    private void updateStartLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        cStartEdit.setText(sdf.format(startCalendar.getTime()));
    }

    private void updateEndLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        cEndEdit.setText(sdf.format(endCalendar.getTime()));
    }

    // do i need onResume here??
    @Override protected void onResume() {

        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getAllAssessments()) {
            if (a.getCourseID() == cID) filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessments(filteredAssessments);
    }
}