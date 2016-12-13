package team7.moviefinder.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import team7.moviefinder.R;
import team7.moviefinder.activities.SingleMovieView;
import team7.moviefinder.adapters.RecyclerViewAdapter;
import team7.moviefinder.app.App;
import team7.moviefinder.constants.Constants;
import team7.moviefinder.controllers.JsonController;
import team7.moviefinder.models.Movie;
import team7.moviefinder.volley.VolleySingleton;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Dion on 12/9/16.
 */

public class MovieListFragment extends Fragment implements RecyclerViewAdapter.OnClickListener, SearchView.OnQueryTextListener {

    //
    Context mContext;
    private static double mLatitude;
    private static double mLongitude;
    private String videoUrl;

    public MovieListFragment(Context mContext) {
        this.mContext = mContext;
    }
    //

    private RecyclerViewAdapter adapter;
    JsonController controller;

    TextView textView;
    RecyclerView recyclerView;
    EditText etSearch;

    FloatingActionButton fab;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    public MovieListFragment getInstance() {
        return new MovieListFragment(this.mContext);
    }

    //
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, mLocationListener);
                }
        }
    }
    //

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)this.getActivity()).setSupportActionBar(toolbar);
        textView = (TextView) view.findViewById(R.id.empty_text_view);
        textView.setText("Search for movies using SearchView in toolbar");
        etSearch = (EditText) view.findViewById(R.id.editText);
        fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        fab.setImageResource(R.drawable.location_icon);

        //
        mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mLatitude = location.getLatitude();
                mLongitude = location.getLongitude();
                Log.e("Latitude: ", String.valueOf(location.getLatitude()));
                Log.e("Longitude: ", String.valueOf(location.getLongitude()));
                Log.e("FAB Lat: ", String.valueOf(mLatitude));
                Log.e("FAB Long: ", String.valueOf(mLongitude));
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        if (ActivityCompat.checkSelfPermission(this.mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.INTERNET
            }, 10);
        }

        //

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 1) {
                    controller.cancelAllRequests();
                    controller.sendRequest(charSequence.toString());
                } else if (charSequence.equals("")) {
                    recyclerView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get the location
                Log.e("FAB", "Floating Action Button Clicked");
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, mLocationListener);
                Log.e("FAB Lat2: ", String.valueOf(mLatitude));
                Log.e("FAB Long2: ", String.valueOf(mLongitude));
                if (mLatitude == 0 || mLongitude == 0) {
                    Toast.makeText(getContext(),
                            "GPS location retrieved. Please click to see the current location",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Latitude: "+
                            String.valueOf(mLatitude) + ", " + "Longitude: "+String.valueOf(mLongitude),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.movie_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerViewAdapter(new ArrayList<Movie>());
        adapter.setListener(this);
        //recyclerView.setAdapter(adapter);

        controller = new JsonController(
                new JsonController.OnResponseListener() {
                    @Override
                    public void onSuccess(List<Movie> movies) {
                        if(movies.size() > 0) {
                            Log.e("TAG","On Success");
                            textView.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.invalidate();
                            adapter.updateDataSet(movies);
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Failed to retrieve data");
                        //Toast.makeText(MainActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                    }
                });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.searchbar, menu);

        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("Search");
        searchView.setSubmitButtonEnabled(true);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if(query.length() > 1) {
            controller.cancelAllRequests();
            controller.sendRequest(query);
            return false;
        } else {
            Toast.makeText(App.getContext(), "Must provide more than one character", Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            textView.setText("Must provide more than one character to search");
            return true;
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.length() > 1) {
            controller.cancelAllRequests();
            controller.sendRequest(newText);
        } else if(newText.equals("")) {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
        return true;

    }

    @Override
    public void onCardClick(Movie movie) {
        TrailerRequest request = new TrailerRequest(movie.getId());
        request.execute(movie.getId() + "");

    }

    @Override
    public void onPosterClick(final Movie movie) { }

    class TrailerRequest extends AsyncTask<String, String, String> {

        private ProgressDialog dialog;
        private boolean gotResp = false;
        private int movieId;

        TrailerRequest(int movieId){
            this.movieId = movieId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(MovieListFragment.this.getContext(),"Loading","Please wait...",false);
        }

        @Override
        protected String doInBackground(String... strings) {

            final String url = Constants.VIDEO_URL_PRE + String.valueOf(movieId) + Constants.VIDEO_URL_POST;
            final String[] key = new String[1];
            Log.e("VideoURL",url);

            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Response ", response.toString());
                            if(response.has("results")){
                                // Get JSONArray from JSONObject
                                JSONArray jsonArray = null;
                                try {
                                    jsonArray = response.getJSONArray("results");
                                    JSONObject jsonObj = jsonArray.getJSONObject(0);
                                    key[0] = (String)jsonObj.get("key");
                                    Log.e("Key",key[0]);
                                    //key[0] = (String)(jsonArray.getJSONObject(0)).get("key");
                                    //Log.e("")
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(MovieListFragment.this.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                            gotResp = true;
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error.Response", error.toString());
                        }
                    }
            );

            // add it to the RequestQueue
            VolleySingleton.getInstance(App.getContext()).addToRequestQueue(getRequest);
            while(true){
                if(gotResp == true)
                    break;
            }
            return key[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            //Log.e("onPostExec",s);
            //videoUrl = s;
            Intent intent = SingleMovieView.newIntent(getActivity(), movieId,s);
            startActivity(intent);
        }
    }
}
