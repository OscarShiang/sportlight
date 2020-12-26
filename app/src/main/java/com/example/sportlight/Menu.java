package com.example.sportlight;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class Menu extends AppCompatActivity {

    ImageView add, reload;
    Button back;
    ListView sportList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findObjects();
        clickEvent();
    }

    public void findObjects() {
        add = findViewById(R.id.add);
        reload = findViewById(R.id.reload);
        back = findViewById(R.id.backButton);

        sportList = findViewById(R.id.sportList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 8080:
                if (resultCode == RESULT_OK) {
                    // TODO: update the event list again
                }
                break;
        }
    }

    public void clickEvent() {


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Menu.this, Create.class);
                startActivityForResult(intent, 8080);
            }
        });

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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