/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.stampmap.service;

import com.example.stampmap.dto.Place;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
@Service
public class UploadService {
    public JSONObject readJsonFromPlaceName(String placeName) {
        try {
            String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?key=AIzaSyDNwAXlo4dArvIJa7J9nKGZUUqJfuYtqgg&language=ja&query=";
            url = url + URLEncoder.encode(placeName, "UTF-8");
            return readJsonFromUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
    }

    private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
}
