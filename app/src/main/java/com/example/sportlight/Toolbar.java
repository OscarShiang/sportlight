package com.example.sportlight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Toolbar extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;
    public ImageView add, reload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.toolbar);
        findObjects();
        setSupportActionBar(toolbar);
        clickEvent();
    }

    public void findObjects() {
        toolbar = findViewById(R.id.toolbar);
        add = findViewById(R.id.addbtn);
        reload = findViewById(R.id.reloadbtn);
    }

    public void clickEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
