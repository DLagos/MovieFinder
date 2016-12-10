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


/*
 * Created by abhijit on 12/2/16.
 */

/**
 * <p> Provides interface between {@link android.app.Activity} and {@link com.android.volley.toolbox.Volley} </p>
 */
public class JsonController {

    private final int TAG = 100;

    private OnResponseListener responseListener;

    /**
     *
     * @param responseListener  {@link OnResponseListener}
     */
    public JsonController(OnResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    /**
     * Adds request to volley request queue
     * @param query query term for search
     */
    public void sendRequest(String query){

        // Request Method
        int method = Request.Method.GET;

        // Url with GET parameters
        String url = Constants.URL + Uri.encode(query);

        // Create new request using JsonRequest
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

        // Add tag to request
        request.setTag(TAG);

        // Get RequestQueue from VolleySingleton
        VolleySingleton.getInstance(App.getContext()).addToRequestQueue(request);
    }

    /**
     * <p>Cancels all request pending in request queue,</p>
     * <p> There is no way to control the request already processed</p>
     */
    public void cancelAllRequests() {
        VolleySingleton.getInstance(App.getContext()).cancelAllRequests(TAG);
    }


    public interface OnResponseListener {
        void onSuccess(List<Movie> movies);
        void onFailure(String errorMessage);
    }

}