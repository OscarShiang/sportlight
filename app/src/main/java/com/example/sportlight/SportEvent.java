package com.example.sportlight;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class SportEvent implements Serializable {
    private int uID;
    private String founder;
    private String sport;
    private String start_at;
    private String participant;

    public SportEvent(JSONObject obj) {
        this.fromJSON(obj);
    }

    public void fromJSON(JSONObject data) {
        try {
            uID = (int) data.get("id");
            founder = (String) data.get("founder");
            sport = (String) data.get("sport");
            start_at = (String) data.get("start_at");
            participant = (String) data.get("participant");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return ("[" + sport + "]     " + founder + "     " + start_at);
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

    public String getParticipant() {
        return participant;
    }
}
