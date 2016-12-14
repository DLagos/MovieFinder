package team7.moviefinder.volley;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import team7.moviefinder.factories.MovieFactory;
import team7.moviefinder.models.Movie;


public class JsonRequest extends Request<List<Movie>> {

    private Response.Listener<List<Movie>> successListener;

    public JsonRequest(int method,
                       String url,
                       Response.Listener<List<Movie>> successListener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.successListener = successListener;
    }

    @Override
    protected Response<List<Movie>> parseNetworkResponse(NetworkResponse response) {
        String jsonString = new String(response.data);
        List<Movie> movies;
        JSONObject jsonObject;
        Log.i(this.getClass().getName(), jsonString);
        try {
            jsonObject = new JSONObject(jsonString);
             movies = MovieFactory.parseJson(jsonObject);
            Log.e("JSOnRequest",""+movies.size())
;        }
        catch (JSONException e) {
            e.printStackTrace();
            return Response.error(new VolleyError("Failed to process the request"));
        }
        return Response.success(movies, getCacheEntry());
    }

    @Override
    protected void deliverResponse(List<Movie> movies) {
        successListener.onResponse(movies);
    }
}
