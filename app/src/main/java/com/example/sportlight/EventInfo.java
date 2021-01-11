package com.example.sportlight;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EventInfo extends AppCompatActivity {

    private ApiEntry apiEntry;

    private Button backBtn, joinBtn, shareBtn;
    private TextView sport, date, time, participants, location;
    private SportEvent info;

    public static final String PACKAGE_NAME = "jp.naver.line.android";
    public static final String CLASS_NAME = "jp.naver.line.android.activity.selectchat.SelectChatActivity";
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
        location = findViewById(R.id.locText);

        backBtn = findViewById(R.id.backButton);
        joinBtn = findViewById(R.id.joinButton);
        shareBtn = findViewById(R.id.shareButton);
    }

    private void setInit() {
        sport.setText(info.getSport());
        String[] start_at = info.getStartAt().split(" ");

        date.setText(start_at[0]);
        time.setText(start_at[1]);

        location.setText(info.getPosition());

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

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                PackageManager pm = getPackageManager();
                List<ApplicationInfo>  appList =  pm.getInstalledApplications(0);
                boolean find = false;
                for(ApplicationInfo app: appList ) {
                    Log.i("info", "app:" + app);
                    if (app.packageName.equals(PACKAGE_NAME)) {
                        find = true;
                        break;
                    }
                }
                if(find) {
                    Intent intent = pm.getLeanbackLaunchIntentForPackage(PACKAGE_NAME);
                    if(intent == null)
                        Log.i("QQ", "QQ");
                    else {
                        //intent.setClassName(PACKAGE_NAME, CLASS_NAME);
                        intent.setType("text/plain");
                        String text = sport.getText().toString();
                        intent.putExtra(Intent.EXTRA_TEXT, text);
                        startActivity(intent);
                    }
                }
                else {
                    Toast.makeText(EventInfo.this, "請先安裝Line再使用分享功能", Toast.LENGTH_LONG).show();
                }
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
                    setResult(RESULT_OK);
                    finish();
                    Toast.makeText(EventInfo.this, "加入成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EventInfo.this, "加入失敗", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}