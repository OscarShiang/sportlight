package com.example.sportlight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    private Button back;
    private TextView rankText, BMIText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        findObjects();
        buttonClickEvent();

        Float BMI = getIntent().getFloatExtra("BMI", -1.0f);
        String rank = getIntent().getStringExtra("result");

        BMIText.setText("你的BMI值：" + BMI.toString());
        rankText.setText("健康狀態：" + rank);
    }

    private void findObjects() {
        back = findViewById(R.id.backButton);

        rankText = findViewById(R.id.rankText);
        BMIText = findViewById(R.id.BMIText);
    }

    private void buttonClickEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}