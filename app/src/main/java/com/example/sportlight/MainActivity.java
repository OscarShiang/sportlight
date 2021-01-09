package com.example.sportlight;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button login, register;
    private EditText usrEdit, pwdEdit;
    private ApiEntry apiEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findObjects();
        buttonClickEvent();

        apiEntry = new ApiEntry();
    }

    public void findObjects() {
        login = findViewById(R.id.loginButton);
        register = findViewById(R.id.registerButton);

        usrEdit = findViewById(R.id.usrEdit);
        pwdEdit = findViewById(R.id.pwdEdit);
    }

    public void buttonClickEvent() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = usrEdit.getText().toString();
                String passwd = pwdEdit.getText().toString();

                if (user.equals("") || passwd.equals("")) {
                    pwdEdit.setText("");
                    Toast.makeText(MainActivity.this, "請輸入完整資訊", Toast.LENGTH_SHORT).show();
                    return;
                }

                final boolean[] ret = new boolean[1];
                ret[0] = false;
                Thread action = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ret[0] = apiEntry.logInAccount(user, passwd);
                    }
                });
                action.start();

                try {
                    action.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!ret[0]) {
                    pwdEdit.setText("");
                    Toast.makeText(MainActivity.this, "帳號或密碼輸入錯誤", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Menu.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }
}