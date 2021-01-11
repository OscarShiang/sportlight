package com.example.sportlight;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    private ImageView add, reload;
    private Button back;

    private RecyclerView recyclerView;
    private Adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<SportEvent> sportEventList; // new

    private ApiEntry apiEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        apiEntry = new ApiEntry();
        findObjects();

        sportEventList = new ArrayList<SportEvent>();
        updateEvents();

        buildRecyclerView();
        clickEvent();
    }

    private void updateEvents() {
        JSONArray[] array = new JSONArray[1];
        Thread action = new Thread(new Runnable() {
            @Override
            public void run() {
                array[0] = apiEntry.getEvents();
            }
        });
        action.start();
        try {
            action.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sportEventList.clear();
        for (int i = 0; i < array[0].length(); i++) {
            try {
                SportEvent element = new SportEvent((JSONObject) array[0].get(i));
                sportEventList.add(element);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        adapter = new Adapter(sportEventList);
    }

    public void findObjects() {
        add = findViewById(R.id.add);
        reload = findViewById(R.id.reload);
        back = findViewById(R.id.backButton);

        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 8080:
            case 8081:
                if (resultCode == RESULT_OK) {
                    updateEvents();
                    buildRecyclerView();
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
                updateEvents();
                buildRecyclerView();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void buildRecyclerView() {
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        adapter = new Adapter(sportEventList);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent();
                intent.setClass(Menu.this, EventInfo.class);
                intent.putExtra("info", sportEventList.get(pos));
                startActivityForResult(intent, 8081);
            }
        });
    }

}