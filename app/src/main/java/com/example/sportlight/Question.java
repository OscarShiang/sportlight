package com.example.sportlight;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Question extends AppCompatActivity {

    private TextView heightText, weightText;
    private Button send;

    private RadioGroup abnoramlRadios;
    private RadioGroup exerciseRadios;
    private RadioGroup fallRadios;

    private ApiEntry apiEntry;

    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        apiEntry = new ApiEntry();

        score = 0;

        findObjects();
        buttonClickEvent();
    }

    public void findObjects() {
        heightText = findViewById(R.id.height);
        weightText = findViewById(R.id.weight);

        send = findViewById(R.id.sendButton);

        abnoramlRadios = findViewById(R.id.abnormal_weight);
        exerciseRadios = findViewById(R.id.exercise_kind);
        fallRadios = findViewById(R.id.fall_down);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 8080:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK);
                    finish();
                }
        }
    }

    public void buttonClickEvent() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp_height = heightText.getText().toString();
                String tmp_weight = weightText.getText().toString();

                if (tmp_height.equals("") || tmp_weight.equals("")) {
                    Toast.makeText(Question.this, "身高或體重不可空白", Toast.LENGTH_SHORT).show();
                    return;
                }

                int height = Integer.parseInt(tmp_height);
                int weight = Integer.parseInt(tmp_weight);


                int tmp;
                tmp = abnoramlRadios.getCheckedRadioButtonId();
                if (tmp == -1) {
                    Toast.makeText(Question.this, "選填題不可空白", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if(tmp == R.id.q2_yes)
                        score++;
                }
                tmp = exerciseRadios.getCheckedRadioButtonId();
                if (tmp == -1) {
                    Toast.makeText(Question.this, "選填題不可空白", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    switch (tmp) {
                        case(R.id.q3_1):
                            score += 2;
                            break;
                        case(R.id.q3_2):
                            score += 1;
                            break;
                        default:
                            break;
                    }
                }
                tmp = fallRadios.getCheckedRadioButtonId();
                if (tmp == -1) {
                    Toast.makeText(Question.this, "選填題不可空白", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if(tmp == R.id.q4_yes)
                        score++;
                }
                final boolean[] ret = new boolean[1];
                Thread action = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ret[0] = apiEntry.setCGAResult(height, weight, score);
                    }
                });
                action.start();
                try {
                    action.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!ret[0]) {
                    Toast.makeText(Question.this, "發生錯誤，請稍後再試", Toast.LENGTH_SHORT).show();
                    return;
                }

                // TODO: define the different ranks
                String result;
                switch (score) {
                    case 0: case 1:
                        result = "健康";
                        break;
                    case 2: case 3:
                        result = "亞健康";
                        break;
                    default:
                        result = "衰弱";
                        break;
                }

                float BMI = (float)weight / (((float) height / 100) * ((float) height / 100));

                Toast.makeText(Question.this, "已儲存填答結果", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.setClass(Question.this, Result.class);
                intent.putExtra("BMI", BMI);
                intent.putExtra("result", result);
                startActivityForResult(intent, 8080);
            }
        });
    }
}