package com.example.myapplication.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.activities.main.MainActivity;

public class LoginActivity extends AppCompatActivity {

    Button signUpButton;
    Button signInButton;
    EditText editTextTextPassword;
    EditText editTextTextPersonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginVM loginVM = new ViewModelProvider(this).get(LoginVM.class);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        signUpButton = findViewById(R.id.signUpButton);
        signInButton = findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = editTextTextPassword.getText().toString();
                String login = editTextTextPersonName.getText().toString();
                loginVM.login(login, password,LoginActivity.this);

                //intent.putExtra("us",LoginVM.this.);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = editTextTextPassword.getText().toString();
                String login = editTextTextPersonName.getText().toString();
                loginVM.registration(login, password);
            }
        });
    }


}