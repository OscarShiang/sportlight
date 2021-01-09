package com.example.sportlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EventInfo extends AppCompatActivity {

    private ApiEntry apiEntry;

    private Button backBtn, joinBtn;
    private TextView sport, date, time, participants;

    private SportEvent info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        apiEntry = new ApiEntry();

        findObjects();
        info = (SportEvent)getIntent().getSerializableExtra("info");
        setInit();

        setClickEvents();
    }

    private void findObjects() {
        sport = findViewById(R.id.sportText);
        date = findViewById(R.id.dateText);
        time = findViewById(R.id.timeText);
        participants = findViewById(R.id.partiText);

        backBtn = findViewById(R.id.backButton);
        joinBtn = findViewById(R.id.joinButton);
    }

    private void setInit() {
        sport.setText(info.getSport());
        String[] start_at = info.getStartAt().split(" ");

        date.setText(start_at[0]);
        time.setText(start_at[1]);

        final String[] partiShowText = new String[1];
        partiShowText[0] = "";

        String[] userIDs = info.getParticipant().split(",");
        Thread action = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < userIDs.length; i++) {
                    if (userIDs[i].equals(""))
                        continue;

                    if (i > 0)
                        partiShowText[0] += ", ";
                    partiShowText[0] += apiEntry.getUsername(Integer.parseInt(userIDs[i]));
                }
            }
        });
        action.start();
        try {
            action.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        participants.setText(partiShowText[0]);
    }

    private void setClickEvents() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean[] ret = new boolean[1];
                Thread action = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ret[0] = apiEntry.joinEvent(info.getUID());
                    }
                });
                action.start();
                try {
                    action.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (ret[0]) {
                    finish();
                    Toast.makeText(EventInfo.this, "加入成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EventInfo.this, "加入失敗", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}