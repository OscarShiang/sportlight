package com.example.sportlight;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class SportEvent implements Serializable {
    private int uID;
    private String founder;
    private String sport;
    private String start_at;

    public SportEvent(int uID, String founder, String sport, String start_at) {
        this.uID = uID;
        this.founder = founder;
        this.sport = sport;
        this.start_at = start_at;
    }

    public SportEvent(JSONObject obj) {
        this.fromJSON(obj);
    }

    public void fromJSON(JSONObject data) {
        try {
            uID = (int) data.get("id");
            founder = (String) data.get("founder");
            sport = (String) data.get("sport");
            start_at = (String) data.get("start_at");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return ("[" + sport + "] " + founder + "\t" + start_at);
    }

    public int getUID() {
        return uID;
    }

    public String getFounder() {
        return founder;
    }

    public String getSport() {
        return sport;
    }

    public String getStartAt() {
        return start_at;
    }
}
