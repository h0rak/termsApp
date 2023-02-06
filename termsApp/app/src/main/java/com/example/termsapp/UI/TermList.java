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
import com.example.termsapp.Entity.Term;
import com.example.termsapp.Entity.User;
import com.example.termsapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TermList extends AppCompatActivity {

    int uID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        MainActivity.matchExists = false;
        uID = getIntent().getIntExtra("uID", -1);
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
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.addSampleTerms:
                Repository repository = new Repository(getApplication());
                Term t1 = new Term(1, "First Term", "04/01/21", "09/31/ 21", uID);
                repository.insert(t1);
                Term t2 = new Term(2, "Second Term", "10/01/21", "03/31/22", uID);
                repository.insert(t2);
                Term t3 = new Term(3, "Third Term", "10/01/21", "03/31/22", uID);
                repository.insert(t3);
                Term t4 = new Term(4, "Fourth term", "10/01/22", "03/31/23", uID);
                repository.insert(t4);
                Term t5 = new Term(5, "Fifth Term", "01/01/23", "06/31/2023", uID);
                repository.insert(t5);
                List<Term> allTerms = repository.getAllTerms();
                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                final TermAdapter adapter = new TermAdapter(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setTerms(allTerms);
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
        /*List<Term> allTerms = repository.getAllTerms();
        termAdapter.setTerms(allTerms);*/
        List<Term> filteredTerms = new ArrayList<>();
        for (Term t : repository.getAllTerms()) {
            if (t.getUserID() == uID) filteredTerms.add(t);
        }
        termAdapter.setTerms(filteredTerms);
    }

}