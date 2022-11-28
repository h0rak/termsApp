package com.example.termsapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.termsapp.Database.Repository;
import com.example.termsapp.Entity.Course;
import com.example.termsapp.Entity.Term;
import com.example.termsapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Moon Phase University");
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // NEW WAY
                Intent intent = new Intent(MainActivity.this,TermList.class);
                Repository repository = new Repository(getApplication());
                Term t1 = new Term(1,"First Term", "04/01/2021", "09/31/2021");
                repository.insert(t1);
                Term t2 = new Term(2,"Second Term", "10/01/2021", "03/31/2022");
                repository.insert(t2);
                Term t3 = new Term(3,"Third Term", "10/01/2021", "03/31/2022");
                repository.insert(t3);
                Term t4 = new Term(4, "Fourth term", "10/01/2022", "03/31/2023");
                repository.insert(t4);
                startActivity(intent);
            }
        });
    }

/*    public void EnterTerms(View view){ // OLD WAY
        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);
        Repository repository = new Repository(getApplication());
        Term t1 = new Term(1,"First Term", "04/01/2021", "09/31/2021");
        repository.insert(t1);
        Term t2 = new Term(2,"Second Term", "10/01/2021", "03/31/2022");
        repository.insert(t2);
        Term t3 = new Term(3,"Third Term", "10/01/2021", "03/31/2022");
        repository.insert(t3);
        Term t4 = new Term(4, "Fourth term", "10/01/2022", "03/31/2023");
        repository.insert(t4);
    }*/

}