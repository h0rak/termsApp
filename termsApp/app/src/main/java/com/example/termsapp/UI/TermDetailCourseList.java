package com.example.termsapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.termsapp.Database.Repository;
import com.example.termsapp.Entity.Course;
import com.example.termsapp.Entity.Term;
import com.example.termsapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TermDetailCourseList extends AppCompatActivity {

    int termID;
    String name;
    String start;
    String end;
    EditText editName;
    EditText editStart;
    EditText editEnd;
    Repository repository;
    Term currentTerm;
    int numCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail_course_ilst);

        termID = getIntent().getIntExtra("id", -1);

        editName = findViewById(R.id.termNameEditText);
        name = getIntent().getStringExtra("name");
        editName.setText(name);

        editStart = findViewById(R.id.termStartEditText);
        start = getIntent().getStringExtra("start");
        editStart.setText(start);

        editEnd = findViewById(R.id.termEndEditText);
        end = getIntent().getStringExtra("end");
        editEnd.setText(end);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                if (termID == -1){
                    if (repository.getAllTerms().size() == 0) termID = 1;
                    else
                        termID = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermID() + 1;
                    term = new Term(termID, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.insert(term);
                    Toast.makeText(this, "Term Saved Successfully", Toast.LENGTH_LONG).show();
                }
                else {
                    term = new Term(termID, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.update(term);
                    Toast.makeText(this, "Term Updated Successfully", Toast.LENGTH_LONG).show();
                }
                this.finish(); // do this go here? testing for save
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
                    Toast.makeText(TermDetailCourseList.this, "Can't delete a term with courses", Toast.LENGTH_LONG).show();
                }
                this.finish(); // do this go here? testing for save
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onResume() {

        super.onResume();
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