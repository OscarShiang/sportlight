package com.example.sportlight;

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
import java.net.URL;

public class ApiEntry {
    final private String baseURL = "https://sportlight-backend.herokuapp.com/api";

    private Boolean isLogin;
    static private String username;

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
        } finally {
            System.out.println(response.toString());
            Boolean ret = Boolean.parseBoolean(response.toString());
            return ret;
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
        } finally {
            isLogin = Boolean.parseBoolean(response.toString());
            if (isLogin)
                username = user;
            return isLogin;
        }
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

}
