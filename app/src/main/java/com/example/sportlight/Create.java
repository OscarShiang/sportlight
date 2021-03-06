package com.example.sportlight;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Create extends AppCompatActivity {

    private Spinner sportSpin;
    private EditText dateEdit;
    private EditText timeEdit;
    private EditText placeEdit;

    private Button createBtn;

    private ApiEntry apiEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // setting objects
        findObjects();
        setClickEvents();

        String[][] sports = new String[][] {{"健走", "太極拳"}, {"健走", "太極拳", "羽球", "桌球", "騎腳踏車"}, {"健走", "太極拳", "羽球", "桌球", "騎腳踏車", "跑步", "爬山", "游泳"}};
        apiEntry = new ApiEntry();

        final int[] score = new int[1];

        Thread action = new Thread(new Runnable() {
            @Override
            public void run() {
                score[0] = apiEntry.getCGAScore(apiEntry.getUID());
            }
        });

        action.start();
        try {
            action.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int index = 0;
        switch(score[0]) {
            case 0: case 1:
                index = 2;
                break;
            case 2: case 3:
                index = 1;
                break;
            default:
                index = 0;
                break;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sports[index]);
        sportSpin.setAdapter(adapter);
    }

    private void findObjects() {
        sportSpin = findViewById(R.id.choose);

        dateEdit = findViewById(R.id.editTextDate);
        timeEdit = findViewById(R.id.editTextTime);
        placeEdit = findViewById(R.id.place);

        createBtn = findViewById(R.id.createButton);
    }

    private void setClickEvents() {
        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                new DatePickerDialog(Create.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateEdit.setText((year + "-" + (month + 1) + "-" + dayOfMonth));
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        timeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                new TimePickerDialog(Create.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //SimpleDateFormat dataFormat = new SimpleDateFormat("hh/mm");
                        timeEdit.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show();
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sport = sportSpin.getSelectedItem().toString();
                String date = dateEdit.getText().toString();
                String time = timeEdit.getText().toString();
                String pos = placeEdit.getText().toString();

                if (date.equals("") || time.equals("") || pos.equals("")) {
                    Toast.makeText(Create.this, "請輸入完整資訊", Toast.LENGTH_SHORT).show();
                    return;
                }

                System.out.println(date + " " + time);

                boolean[] ret = new boolean[1];
                Thread action = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ret[0] = apiEntry.createEvent(sport, date + " " + time, pos);
                    }
                });
                action.start();
                try {
                    action.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (ret[0]) {
                    Toast.makeText(Create.this, "Event created!", Toast.LENGTH_SHORT);

                    // return
                    Intent intent = new Intent();
                    intent.setClass(Create.this, Menu.class);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}