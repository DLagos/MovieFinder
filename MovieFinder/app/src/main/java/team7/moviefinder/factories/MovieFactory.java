package team7.moviefinder.factories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import team7.moviefinder.models.Movie;

/**
 * Created by Dion on 12/9/16.
 */

public class MovieFactory {

    public static List<Movie> parseJson(JSONObject jsonObject) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        // Check if the JSONObject has object with key "Search"
        if(jsonObject.has("Search")){
            // Get JSONArray from JSONObject
            JSONArray jsonArray = jsonObject.getJSONArray("Search");
            for(int i = 0; i < jsonArray.length(); i++){
                // Create new Movie object from each JSONObject in the JSONArray
                movies.add(new Movie(jsonArray.getJSONObject(i)));
            }
        }

        return movies;
    }
}
