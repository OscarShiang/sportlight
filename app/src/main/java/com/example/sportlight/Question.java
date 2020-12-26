package com.example.sportlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Question extends AppCompatActivity {

    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        findObjects();
        buttonClickEvent();
    }

    public void findObjects() {
        send = findViewById(R.id.sendButton);
    }

    public void buttonClickEvent() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Question.this, Result.class);
                startActivityForResult(intent);
            }
        });
    }

    private void startActivityForResult(Intent intent) {
        finish();
    }
}