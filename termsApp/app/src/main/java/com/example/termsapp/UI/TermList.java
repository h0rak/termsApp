package com.example.termsapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.termsapp.Database.Repository;
import com.example.termsapp.Entity.Assessment;
import com.example.termsapp.Entity.Course;
import com.example.termsapp.Entity.Term;
import com.example.termsapp.Entity.User;
import com.example.termsapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TermList extends AppCompatActivity {

    int uID;
    int totalTerms;
    int totalCourses;
    int totalAssessments;
    String reportBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        MainActivity.matchExists = false;
        uID = getIntent().getIntExtra("l_uID", -1);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Repository repository = new Repository(getApplication());
        final TermAdapter termAdapter = new TermAdapter(this); // create a final adapter
        recyclerView.setAdapter(termAdapter); // set adapter to recycler
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // set layout of recycler
        /*List<Term> terms = repository.getAllTerms();
        termAdapter.setTerms(terms); // set the items in - using the adapter*/
        List<Term> filteredTerms = new ArrayList<>();
        for (Term t: repository.getAllTerms()) {
            if (t.getUserID() == uID) filteredTerms.add(t);
        }
        termAdapter.setTerms(filteredTerms);

        FloatingActionButton button = findViewById(R.id.floatingActionButton); // floatingActionButton cannot be casted as a regular Button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermList.this, TermDetailCourseList.class);
                intent.putExtra("uID", uID);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_termlist, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Repository repository = new Repository(getApplication());
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.shareReport:
                totalTerms = repository.getAllTerms().size();
                totalCourses = repository.getAllCourses().size();
                totalAssessments = repository.getAllAssessments().size();
                List<Integer> userTermIDs = new ArrayList<>();
                List<Integer> userCourseIDs = new ArrayList<>();
                List<Integer> userAssessmentIDs = new ArrayList<>();
                for (Term t : repository.getAllTerms()) {
                    if (t.getUserID() == uID) {
                        userTermIDs.add(t.getTermID());
                    }
                }
                for (Course c : repository.getAllCourses()) {
                    for (int userTermID : userTermIDs) {
                        if (userTermID == c.getTermID()) {
                            userCourseIDs.add(userTermID);
                        }
                    }
                }
                for (Assessment a : repository.getAllAssessments()) {
                    for (int userCourseID : userCourseIDs) {
                        if (userCourseID == a.getCourseID()) {
                            userAssessmentIDs.add(userCourseID);
                        }
                    }
                }

                reportBuilder = "Report\n\nUser Totals\nTerms: " + userTermIDs.size() + "\nCourses: " + userCourseIDs.size() + "\nAssessments: " + userAssessmentIDs.size();
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_TITLE, "Choose Report Sharing Option");
                intent1.putExtra(Intent.EXTRA_TEXT, reportBuilder);
                intent1.setType("text/plain");
                Intent intent2 = Intent.createChooser(intent1, null);
                startActivity(intent2);
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Repository repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Term> filteredTerms = new ArrayList<>();
        for (Term t : repository.getAllTerms()) {
            if (t.getUserID() == uID) filteredTerms.add(t);
        }
        termAdapter.setTerms(filteredTerms);
    }

}