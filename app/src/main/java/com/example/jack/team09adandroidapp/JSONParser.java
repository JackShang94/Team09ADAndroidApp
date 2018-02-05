
package com.example.jack.team09adandroidapp;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by doujohner on 28/1/2018.
 */

public class JSONParser {
    static String readStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            is.close();
        } catch (Exception e) {
            Log.e("readStream Exception", StackTrace.trace(e));
        }
        return(sb.toString());
    }
    public static String getStream(String url) {
        InputStream is = null;
        StringBuilder sb = new StringBuilder();
        String testName = "testuser";
        String testPwd = "testpassword";
        byte[] credential = (testName+":"+testPwd).getBytes();
        StringBuilder c = new StringBuilder()
                .append("Basic ")
                .append(Base64.encodeToString(credential,Base64.DEFAULT));
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.addRequestProperty("Authorization",c.toString());
            conn.setRequestMethod("GET");
            conn.connect();
            is = conn.getInputStream();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return(sb.toString());
    }
    public static String postStream(String url, String data) {
        InputStream is = null;
        try {
            String testName = "testuser";
            String testPwd = "testpassword";
            byte[] credential = (testName+":"+testPwd).getBytes();
            StringBuilder c = new StringBuilder()
                    .append("Basic ")
                    .append(Base64.encodeToString(credential,Base64.DEFAULT));
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.addRequestProperty("Authorization",c.toString());
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setFixedLengthStreamingMode(data.getBytes().length);
            conn.connect();
            OutputStream os = new BufferedOutputStream(conn.getOutputStream());
            os.write(data.getBytes());
            os.flush();
            is = conn.getInputStream();
        } catch (UnsupportedEncodingException e) {
            Log.e("postStream Exception", StackTrace.trace(e));
        } catch (Exception e) {
            Log.e("postStream Exception", StackTrace.trace(e));
        }
        return readStream(is);
    }
    public static JSONObject getJSONFromUrl(String url) {
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(getStream(url));
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObj;
    }

    public static JSONArray getJSONArrayFromUrl(String url) {
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(getStream(url));
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing array " + e.toString());
        }
        return jArray;
    }
}
