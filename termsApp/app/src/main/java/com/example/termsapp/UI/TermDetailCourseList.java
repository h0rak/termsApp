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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.termsapp.Database.Repository;
import com.example.termsapp.Entity.Course;
import com.example.termsapp.Entity.Term;
import com.example.termsapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetailCourseList extends AppCompatActivity {

    int termID;
    String name;
    String start;
    String end;
    int userID;
    EditText editName;
    EditText editStart;
    EditText editEnd;
    EditText editSearch;
    Button searchButton;
    Repository repository;
    Term currentTerm;
    int numCourses;
    Button saveTermButton;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail_course_ilst);

        termID = getIntent().getIntExtra("t_tID", -1);

        editName = findViewById(R.id.termNameEditText);
        name = getIntent().getStringExtra("t_name");
        editName.setText(name);
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        editStart = findViewById(R.id.termStartEditText);
        start = getIntent().getStringExtra("t_start");
        if (start == null) {
            editStart.setText(sdf.format(new Date()));
        }
        else {
            editStart.setText(start);
        }

        editEnd = findViewById(R.id.termEndEditText);
        end = getIntent().getStringExtra("t_end");
        if (end == null) {
            editEnd.setText(sdf.format(new Date()));
        }
        else {
            editEnd.setText(end);
        }

        editStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateToString = editStart.getText().toString();
                try {
                    startCalendar.setTime(sdf.parse(dateToString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetailCourseList.this, startDate, startCalendar.get(Calendar.YEAR),
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

        editEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateToString = editStart.getText().toString();
                try {
                    endCalendar.setTime(sdf.parse(dateToString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetailCourseList.this, endDate, endCalendar.get(Calendar.YEAR),
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        repository = new Repository(getApplication());

        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()) {
            if (c.getTermID() == termID) filteredCourses.add(c);
        }
        courseAdapter.setCourses(filteredCourses);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermDetailCourseList.this, CourseDetailAssessmentList.class);
                intent.putExtra("termID", termID);
                startActivity(intent);
            }
        });

        userID = getIntent().getIntExtra("uID", -1);
        editSearch = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchString = editSearch.getText().toString().toLowerCase();
                List<Course> searchedCourses = new ArrayList<>();
                if (editSearch.getText().toString().isEmpty()) {
                    courseAdapter.setCourses(filteredCourses);
                }
                else {
                    for (Course c : repository.getAllCourses()) {
                        if (c.getTermID() == termID && c.getCourseName().toLowerCase().contains(searchString)) {
                            searchedCourses.add(c);
                        }
                    }
                    courseAdapter.setCourses(searchedCourses);
                    recyclerView.setAdapter(courseAdapter);
                }
            }
        });

        saveTermButton = findViewById(R.id.saveTermButton);
        saveTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Term term;
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                Date d1 = null;
                Date d2 = null;
                try {
                    d1 = sdf.parse(editStart.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    d2 = sdf.parse(editEnd.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (d2.before(d1)) {
                    Toast.makeText(TermDetailCourseList.this, getString(R.string.dateError), Toast.LENGTH_LONG).show();
                }
                else {
                    if (termID == -1) {
                        if (repository.getAllTerms().size() == 0)
                            termID = 1;
                        else
                            termID = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermID() + 1;
                        term = new Term(termID, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), userID);
                        repository.insert(term);
                        Toast.makeText(TermDetailCourseList.this, "Term Saved Successfully", Toast.LENGTH_LONG).show();
                    } else {
                        term = new Term(getIntent().getIntExtra("t_tID", -1), editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), getIntent().getIntExtra("t_uID", -1));
                        repository.update(term);
                        Toast.makeText(TermDetailCourseList.this, "Term Updated Successfully", Toast.LENGTH_LONG).show();
                    }
                    TermDetailCourseList.this.finish();
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_termdetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.saveTerm:
                Term term;
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                Date d1 = null;
                Date d2 = null;
                try {
                    d1 = sdf.parse(editStart.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    d2 = sdf.parse(editEnd.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (d2.before(d1)) {
                    Toast.makeText(TermDetailCourseList.this, getString(R.string.dateError), Toast.LENGTH_LONG).show();
                }
                else {
                    if (termID == -1) {
                        if (repository.getAllTerms().size() == 0) termID = 1;
                        else
                            termID = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermID() + 1;
                        term = new Term(termID, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), userID);
                        repository.insert(term);
                        Toast.makeText(this, "Term Saved Successfully", Toast.LENGTH_LONG).show();
                    } else {
                        term = new Term(getIntent().getIntExtra("t_tID", -1), editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), getIntent().getIntExtra("t_uID", -1));
                        repository.update(term);
                        Toast.makeText(this, "Term Updated Successfully", Toast.LENGTH_LONG).show();
                    }
                    this.finish();
                }
                return true;
            case R.id.deleteTerm:
                for (Term t : repository.getAllTerms()) {
                    if (t.getTermID() == termID) currentTerm = t;
                }

                numCourses = 0;
                for (Course course : repository.getAllCourses()) {
                    if (course.getTermID() == termID) ++numCourses;
                }

                if (numCourses == 0) {
                    repository.delete(currentTerm);
                    Toast.makeText(TermDetailCourseList.this, currentTerm.getTermName() + " was deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TermDetailCourseList.this, "Can't delete a term with course(s)", Toast.LENGTH_LONG).show();
                }
                this.finish(); // do this go here? testing for save
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void updateStartLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStart.setText(sdf.format(startCalendar.getTime()));
    }

    private void updateEndLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editEnd.setText(sdf.format(endCalendar.getTime()));
    }

    @Override
    protected void onResume() {

        super.onResume();
        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()) {
            if (c.getTermID() == termID) filteredCourses.add(c);
        }
        courseAdapter.setCourses(filteredCourses);
    }

}