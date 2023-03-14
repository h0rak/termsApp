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

public class LoginActivity extends AppCompatActivity {

    EditText usernameEdit;
    EditText passwordEdit;
    int idToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        usernameEdit = findViewById(R.id.usernameInput);
        passwordEdit = findViewById(R.id.passwordInput);

        Repository repository = new Repository(getApplication());
        User user1 = new User(1, "test", "test");
        repository.insert(user1);
        User user2 = new User(2, "admin", "admin");
        repository.insert(user2);

        Button signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, NewUserActivity.class);
                startActivity(intent);
            }
        });

        Button clear = findViewById(R.id.clearButton);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // NEW WAY
                usernameEdit.setText("");
                passwordEdit.setText("");
            }
        });

        Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.matchExists = false;
                for (User u : repository.getAllUsers()) {
                    if (u.getUserName().equals(usernameEdit.getText().toString()) && u.getUserPass().equals(passwordEdit.getText().toString())) {
                        idToSend = u.getUserID();
                        MainActivity.matchExists = true;
                        break;
                    }
                }
                if (MainActivity.matchExists) {
                    Intent intent = new Intent(LoginActivity.this, TermList.class);
                    intent.putExtra("l_uID", idToSend);
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                } else if (usernameEdit.getText().toString().equals("") && passwordEdit.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Enter username and password", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(LoginActivity.this, "Invalid username and password", Toast.LENGTH_LONG).show();
                    usernameEdit.setText("");
                    passwordEdit.setText("");
            }

        });
    }

}
