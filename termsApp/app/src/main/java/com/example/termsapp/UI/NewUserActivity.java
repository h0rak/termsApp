package com.example.termsapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.termsapp.Database.Repository;
import com.example.termsapp.Entity.User;
import com.example.termsapp.R;

public class NewUserActivity extends AppCompatActivity {

    EditText userEdit;
    EditText userPass;
    Button save;
    Button clear;
    Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        repository = new Repository(getApplication());
        userEdit = findViewById(R.id.newUserEdit);
        userPass = findViewById(R.id.newPassEdit);
        save = findViewById(R.id.saveUserButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.matchExists = false;
                for (User u : repository.getAllUsers()) {
                    if (u.getUserName().equals(userEdit.getText().toString())) {
                        MainActivity.matchExists = true;
                        break;
                    }
                }
                if (MainActivity.matchExists) {
                    Toast.makeText(NewUserActivity.this, "Username unavailable", Toast.LENGTH_LONG).show();
                    userEdit.setText("");
                    userPass.setText("");
                }
                else {
                    User user = new User(repository.getAllUsers().get(repository.getAllUsers().size()-1).getUserID() + 1, userEdit.getText().toString(), userPass.getText().toString());
                    repository.insert(user);
                    Toast.makeText(NewUserActivity.this, "User Created Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(NewUserActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });

        clear = findViewById(R.id.clearUserButton);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdit.setText("");
                userPass.setText("");
            }
        });
    }
}
