package com.example.termsapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import com.example.termsapp.Database.Repository;
import com.example.termsapp.Entity.Course;
import com.example.termsapp.Entity.Term;
import com.example.termsapp.R;

import java.util.ArrayList;
import java.util.List;

public class TermDetailCourseList extends AppCompatActivity {

    int id;
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
        setContentView(R.layout.activity_term_detail);

        id = getIntent().getIntExtra("id", -1);

        editName = findViewById(R.id.termNameEditText);
        name = getIntent().getStringExtra("name");
        editName.setText(name);

        editStart = findViewById(R.id.termStartEditText);
        start = getIntent().getStringExtra("start");
        editStart.setText(start);

        editEnd = findViewById(R.id.termEndEditText);
        end = getIntent().getStringExtra("end");
        editEnd.setText(end);

        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        Repository repository = new Repository(getApplication());
//        List<Course> courses = repository.getAssociatedCourses();

        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()) {
            if (c.getTermID() == id) filteredCourses.add(c);
        }
        courseAdapter.setCourses(filteredCourses);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_termdetail, menu);
        return true;
    }

    public boolean onOptionItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.saveTerm:
                // code to save term
                if (id == -1){
                    currentTerm = new Term(0, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.insert(currentTerm);
                }
                else {
                    currentTerm = new Term(id, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.update(currentTerm);
                }
                return true;

            case R.id.deleteTerm:
                // code to delete term
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}