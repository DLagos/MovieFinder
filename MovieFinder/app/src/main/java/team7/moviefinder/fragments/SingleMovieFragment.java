package team7.moviefinder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import team7.moviefinder.R;
import team7.moviefinder.app.App;
import team7.moviefinder.constants.Constants;
import team7.moviefinder.factories.MovieFactory;
import team7.moviefinder.models.Movie;
import team7.moviefinder.volley.VolleySingleton;
/**
 * Created by Dion on 12/9/16.
 */

public class SingleMovieFragment extends Fragment {

    private static final String ARG_MOVIE_ID = "movie_id";
    private static final String ARG_VIDEO_ID = "video_id";

    private Movie mMovie;
    //title, id, overview, releasedate, voteavg, posterurl
    private TextView mTitle;
    private TextView mOverview;
    private TextView mRelease_date;
    private TextView mAvg;
    private NetworkImageView poster;
    private String videoId;
    //ImageLoader imageLoader = VolleySingleton.getInstance(App.getContext()).getImageLoader();
    private YouTubePlayer YPlayer;

    public static SingleMovieFragment newInstance(int id, String videoID){
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE_ID, id);
        args.putSerializable(ARG_VIDEO_ID,videoID);

        SingleMovieFragment fragment = new SingleMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_single_movie, container, false);
        mTitle = (TextView) v.findViewById(R.id.single_movie_title_text_view);
        mAvg = (TextView) v.findViewById(R.id.single_movie_avg);
        mOverview = (TextView) v.findViewById(R.id.single_movie_description_text_view);
        // mAvg = (TextView) v.findViewById(R.id.single_movie_rating);
        mRelease_date = (TextView) v.findViewById(R.id.single_movie_date);
        poster = (NetworkImageView) v.findViewById(R.id.single_movie_poster_image_view);
        mTitle.setText(mMovie.getTitle());
        mOverview.setText(mMovie.getOverview());
        mRelease_date.setText(mMovie.getDate());
        ImageLoader imgLoader = VolleySingleton.getInstance(App.getContext()).getImageLoader();
        poster.setImageUrl(mMovie.getPosterUrl(), imgLoader);
        mAvg.setText(String.valueOf(mMovie.getAvg()));

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(Constants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(Provider arg0, final YouTubePlayer youTubePlayer, boolean b) {
            YPlayer = youTubePlayer;
                YPlayer.loadVideo(videoId);
                youTubePlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {

               private boolean interceptPlay = true;
                @Override
                public void onPlaying() {
                    if(interceptPlay){
                        youTubePlayer.pause();
                        interceptPlay = false;
                    }
                }

                @Override
                public void onPaused() {

                }

                @Override
                public void onStopped() {

                }

                @Override
                public void onBuffering(boolean b) {

                }

                @Override
                public void onSeekTo(int i) {

                }
            });


            }
            @Override
            public void onInitializationFailure(Provider arg0, YouTubeInitializationResult arg1) {
                Log.e("NO WORK", arg1.toString());


            }
        });
        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int movieId = (int) getArguments().getSerializable(ARG_MOVIE_ID);
        videoId = (String) getArguments().getSerializable(ARG_VIDEO_ID);
        mMovie = MovieFactory.getInstance().getMovie(movieId);


    }

}