package com.example.sportlight;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private Button confirm, back;
    private EditText usrEdit, pwdEdit;
    private ApiEntry apiEntry;

    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // find the objects
        findObjects();

        apiEntry = new ApiEntry();

        buttonClickEvent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 8000:
                if (resultCode == RESULT_OK) {
                    finish();
                }
                break;
        }
    }

    public void findObjects() {
        // EditTests
        usrEdit = findViewById(R.id.usrEdit);
        pwdEdit = findViewById(R.id.pwdEdit);

        // Buttons
        confirm = findViewById(R.id.confirmButton);
        back = findViewById(R.id.backButton);
    }

    public void buttonClickEvent() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usrEdit.getText().toString();
                password = pwdEdit.getText().toString();

                if (username.equals("") || password.equals("")) {
                    Toast.makeText(Register.this, "Please enter the username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                final boolean[] ret = new boolean[1];
                Thread action = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ret[0] = apiEntry.createAccount(username, password);
                    }
                });
                action.start();

                try {
                    action.join();
                    Toast.makeText(Register.this, "The result " + ret[0], Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // TODO: enter question activity and finish the questionnaire
                Intent intent = new Intent();
                intent.setClass(Register.this, Question.class);
                startActivityForResult(intent, 8000);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}