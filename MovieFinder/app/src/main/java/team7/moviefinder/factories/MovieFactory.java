package team7.moviefinder.factories;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import team7.moviefinder.controllers.JsonController;
import team7.moviefinder.models.Movie;
import team7.moviefinder.volley.JsonRequest;

/**
 * Created by Dion on 12/9/16.
 */

public class MovieFactory {

    private static MovieFactory mMovieFactory;

    private static List<Movie> mMovies;

    public static MovieFactory getInstance(){

        if(mMovieFactory == null){
            mMovieFactory = new MovieFactory();
        }

        return mMovieFactory;
    }


    public static List<Movie> parseJson(JSONObject jsonObject) throws JSONException {
        mMovies = new ArrayList<>();
        if(jsonObject.has("results")){
            // Get JSONArray from JSONObject
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for(int i = 0; i < jsonArray.length(); i++){
                // Create new Movie object from each JSONObject in the JSONArray
                mMovies.add(new Movie(jsonArray.getJSONObject(i)));

            }
        }

        return mMovies;
    }


    public List<Movie> getMovies() {
        return mMovies;
    }

    public List<Movie> searchMovies(String title){
        List<Movie> movies = new ArrayList<>();
        for(Movie s: mMovies){
            if(s.getTitle().contains(title)){
                movies.add(s);
            }
        }
        return movies;
    }

    public Movie getMovie(int id) {
        for (Movie movie : mMovies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }
}
