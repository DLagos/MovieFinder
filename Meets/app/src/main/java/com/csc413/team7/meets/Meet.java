package com.csc413.team7.meets;

import android.text.Html;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dion on 12/7/16.
 */

public class Meet {
    private String name;
    private String id;
    private int attendees;
    private String streetAddress;
    private String city;
    private String zip;
    private String time;
    private String url;
    private String description;
    private SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM, yyyy HH:mm:ss");

    public static List<Meet> parseJson(JSONObject jsonObject) throws JSONException {
        List<Meet> events = new ArrayList<>();
        if(jsonObject.has("Search")){
            JSONArray jsonArray = jsonObject.getJSONArray("Search");
            for(int i = 0; i < jsonArray.length(); i++){
                events.add(new Meet(jsonArray.getJSONObject(i)));
            }
        }

        return events;
    }

    private Meet(JSONObject jsonObject) throws JSONException {
        if(jsonObject.has("name")) this.setName(jsonObject.getString("name"));
        if(jsonObject.has("id")) this.setId(jsonObject.getString("id"));
        if(jsonObject.has("yes_rsvp_count")) this.setAttendees(jsonObject.getInt("yes_rsvp_count"));
        if(jsonObject.has("address_1")) this.setStreetAddress(jsonObject.getString("address_1"));
        if(jsonObject.has("city")) this.setCity(jsonObject.getString("city"));
        if(jsonObject.has("zip")) this.setZip(jsonObject.getString("zip"));
        if(jsonObject.has("link")) this.setUrl(jsonObject.getString("link"));
        if(jsonObject.has("description")) this.setDescription(stripHtml(jsonObject.getString("description")));
        if(jsonObject.has("time")) this.setTime(formatter.format(new Date(Long.valueOf(jsonObject.getInt("time")))));

    }
    public String stripHtml(String html) {
        return Html.fromHtml(html).toString();
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public int getAttendees() {return attendees;}

    public void setAttendees(int attendees) {this.attendees = attendees;}

    public String getStreetAddress() {return streetAddress;}

    public void setStreetAddress(String streetAddress) {this.streetAddress = streetAddress;}

    public String getCity() {return city;}

    public void setCity(String city) {this.city = city;}

    public String getZip() {return zip;}

    public void setZip(String zip) {this.zip = zip;}

    public String getUrl() {return url;}

    public void setUrl(String url) {this.url = url;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public String getTime() {return time;}

    public void setTime(String time) {this.time = time;}

}
