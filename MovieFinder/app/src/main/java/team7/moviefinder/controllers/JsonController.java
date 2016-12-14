package team7.moviefinder.controllers;

import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

import team7.moviefinder.app.App;
import team7.moviefinder.constants.Constants;
import team7.moviefinder.models.Movie;
import team7.moviefinder.volley.JsonRequest;
import team7.moviefinder.volley.VolleySingleton;


public class JsonController {

    private final int TAG = 100;

    private OnResponseListener responseListener;

    public JsonController(OnResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    public void sendRequest(String query){
        int method = Request.Method.GET;
        String url = Constants.URL + Uri.encode(query);
        JsonRequest request
            = new JsonRequest(
                method,
                url,
                new Response.Listener<List<Movie>>() {
                    @Override
                    public void onResponse(List<Movie> movies) {
                        responseListener.onSuccess(movies);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseListener.onFailure(error.getMessage());
                    }
                }
        );
        request.setTag(TAG);
        VolleySingleton.getInstance(App.getContext()).addToRequestQueue(request);
    }

    public void cancelAllRequests() {
        VolleySingleton.getInstance(App.getContext()).cancelAllRequests(TAG);
    }

    public interface OnResponseListener {
        void onSuccess(List<Movie> movies);
        void onFailure(String errorMessage);
    }

}