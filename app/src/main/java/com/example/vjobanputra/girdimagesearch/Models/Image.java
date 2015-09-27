package com.example.vjobanputra.girdimagesearch.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vjobanputra on 9/24/15.
 */
public class Image implements Serializable {
    private String title;
    private String url;
    private String thumbnail;

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Image(JSONObject json) {

        try {
            this.url = json.getString("url");
            this.thumbnail = json.getString("tbUrl");
            this.title = json.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Image> fromJsonArray(JSONArray array) {
        ArrayList<Image> ret = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                ret.add(new Image(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }
}
