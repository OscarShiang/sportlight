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
    private ListView sportList;
    private ArrayList<SportEvent> events; // old

    private RecyclerView recyclerView;
    private Adapter newAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<SportEvent> sportEventList; // new

    private ApiEntry apiEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        apiEntry = new ApiEntry();

        findObjects();
        clickEvent();

        sportList.setVisibility(View.INVISIBLE);

        events = new ArrayList<SportEvent>();
        sportEventList = new ArrayList<SportEvent>();
        updateEvents();
        buildRecyclerView();
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

        events.clear();
        sportEventList.clear();
        for (int i = 0; i < array[0].length(); i++) {
            try {
                JSONObject data = (JSONObject) array[0].get(i);
                SportEvent element = new SportEvent((JSONObject) array[0].get(i));
                events.add(element);
                sportEventList.add(element);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(Menu.this, android.R.layout.simple_list_item_1, events);
        sportList.setAdapter(adapter);

        newAdapter = new Adapter(sportEventList);
    }

    public void findObjects() {
        add = findViewById(R.id.add);
        reload = findViewById(R.id.reload);
        back = findViewById(R.id.backButton);

        sportList = findViewById(R.id.sportList);

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

        sportList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(Menu.this, EventInfo.class);
                intent.putExtra("info", events.get(position));
                startActivityForResult(intent, 8081);
            }
        });
    }

    public void buildRecyclerView() {
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        newAdapter = new Adapter(sportEventList);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(newAdapter);
    }

}