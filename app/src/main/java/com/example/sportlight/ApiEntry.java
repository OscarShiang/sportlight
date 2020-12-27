package com.example.sportlight;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ApiEntry {
    final private String baseURL = "https://sportlight-backend.herokuapp.com/api";

    static private Boolean isLogin;

    static private String username;
    static private int uID;

    public ApiEntry(){
        isLogin = false;
    }

    public boolean isUserLogin() {
        return isLogin;
    }

    public Boolean createAccount(String user, String passwd) {

        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(baseURL + "/account/signup");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream out = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

            JSONObject data = new JSONObject();
            data.put("user", user);
            data.put("passwd", passwd);

            writer.write(data.toString());
            writer.flush();
            writer.close();
            out.close();

            InputStream ipt = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ipt));

            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject res_data = new JSONObject(response.toString());
            boolean status = (boolean) res_data.get("status");
            if (status) {
                JSONObject info = (JSONObject) res_data.get("info");

                username = (String) info.get("user");
                uID = (int) info.get("id");
            }
            return status;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean logInAccount(String user, String passwd) {

        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(baseURL + "/account/signin");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream out = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            JSONObject json = new JSONObject();
            json.put("user", user);
            json.put("passwd", passwd);

            writer.write(json.toString());
            writer.flush();
            writer.close();

            InputStream ipt = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ipt));

            String line;
            while((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (response.toString().equals("")) {
            return false;
        } else {
            try {
                JSONObject res_data = new JSONObject(response.toString());
                uID = (int) res_data.get("id");
                username = (String) res_data.get("user");

            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    public JSONArray getEvents() {
        StringBuilder response = new StringBuilder();
        JSONObject ret = null;

        try {
            URL url = new URL(baseURL + "/event");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");

            InputStream ipt = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ipt));

            String line;
            while((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            conn.disconnect();

            ret = new JSONObject(response.toString());

            return ret.getJSONArray("events");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new JSONArray();
    }

    public Boolean createEvent(String sport, String start_at) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(baseURL + "/event");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");

            OutputStream out = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            JSONObject body = new JSONObject();
            body.put("founder", username);
            body.put("sport", sport);
            body.put("start_at", start_at);

            writer.write(body.toString());
            writer.flush();
            writer.close();

            InputStream ipt = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ipt));

            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            Boolean ret = Boolean.parseBoolean(response.toString());
            return ret;
        }
    }

    public boolean joinEvent(int event_id) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(baseURL + "/event/join");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            OutputStream out = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            JSONObject body = new JSONObject();
            body.put("event_id", event_id);
            body.put("user_id", uID);

            writer.write(body.toString());
            writer.flush();
            writer.close();

            InputStream ipt = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ipt));

            String line;
            while((line = reader.readLine()) != null) {
                response.append(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            Boolean ret = Boolean.parseBoolean(response.toString());
            return ret;
        }
    }

    public String getUsername(int user_id) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(baseURL + "/account/" + user_id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(false);

            InputStream ipt = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ipt));

            String line;
            while((line = reader.readLine()) != null) {
                response.append(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject ret = new JSONObject(response.toString());
            return (String) ret.get("user");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean setCGAResult(int height, int weight, boolean abnormal_weight, int exercise_kind, boolean fall_down) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(baseURL + "/cga");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            OutputStream out = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            JSONObject body = new JSONObject();
            body.put("id", uID);
            body.put("height", height);
            body.put("weight", weight);
            body.put("abnormal_weight", abnormal_weight);
            body.put("exercise", exercise_kind);
            body.put("fall_down", fall_down);

            writer.write(body.toString());
            writer.flush();
            writer.close();

            InputStream ipt = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ipt));

            String line;
            while((line = reader.readLine()) != null) {
                response.append(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            Boolean ret = Boolean.parseBoolean(response.toString());
            return ret;
        }
    }
}
