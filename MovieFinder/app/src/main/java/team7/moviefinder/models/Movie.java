package team7.moviefinder.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dion on 12/9/16.
 */

public class Movie {
    private String title;
    private int id;
    private String overview;
    private String release_date;
    private double vote_average;
    private String posterUrl;

    public Movie(JSONObject jsonObject) throws JSONException {
        if(jsonObject.has("original_title")) this.setTitle(jsonObject.getString("original_title"));
        if(jsonObject.has("release_date")) this.setDate(jsonObject.getString("release_date"));
        if(jsonObject.has("overview")) this.setOverview(jsonObject.getString("overview"));
        if(jsonObject.has("id")) this.setId(jsonObject.getInt("id"));
        if(jsonObject.has("vote_average")) this.setAvg(jsonObject.getDouble("vote_average"));
        if(jsonObject.has("poster_path")) this.setPosterUrl("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + jsonObject.getString("poster_path"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDate() {
        return release_date;
    }

    public void setDate(String release_date) {
        this.release_date = release_date;
    }

    public double getAvg() {
        return vote_average;
    }

    public void setAvg(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
