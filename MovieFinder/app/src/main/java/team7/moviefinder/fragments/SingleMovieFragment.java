package team7.moviefinder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import team7.moviefinder.R;
import team7.moviefinder.models.Movie;

import team7.moviefinder.factories.MovieFactory;

/**
 * Created by Dion on 12/9/16.
 */

public class SingleMovieFragment extends Fragment {

    private static final String ARG_MOVIE_ID = "movie_id";
    private Movie mMovie;
    //title, id, overview, releasedate, voteavg, posterurl
    private TextView mTitle;
    private TextView mId;
    private TextView mOverview;
    private TextView mRelease_date;
    private TextView mAvg;
    private NetworkImageView poster;

    public static SingleMovieFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE_ID, id);

        SingleMovieFragment fragment = new SingleMovieFragment();
        fragment.setArguments(args);
        return fragment;    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_single_movie, container, false);
        mTitle = (TextView) v.findViewById(R.id.single_movie_title_text_view);
        //mId = (TextView) v.findViewById(R.id.single_movie_avg);
        mOverview = (TextView) v.findViewById(R.id.single_movie_description_text_view);
        mRelease_date = (TextView) v.findViewById(R.id.single_movie_date);
        poster = (NetworkImageView) v.findViewById(R.id.single_movie_poster_image_view);
        mTitle.setText(mMovie.getTitle());
//        mId.setText(mMovie.getId());
        mOverview.setText(mMovie.getOverview());
        mRelease_date.setText(mMovie.getDate());
        //mAvg.setText(String.valueOf(mMovie.getAvg()));
        //poster.setImageBitmap(mMovie.getPosterUrl());
        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int movieId = (int) getArguments().getSerializable(ARG_MOVIE_ID);
        mMovie = MovieFactory.getInstance().getMovie(movieId);
    }
}
