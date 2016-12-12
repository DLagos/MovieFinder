package team7.moviefinder.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

import team7.moviefinder.fragments.SingleFragmentActivity;
import team7.moviefinder.fragments.SingleMovieFragment;

/**
 * Created by Dion on 12/9/16.
 */

public class SingleMovieView extends SingleFragmentActivity {
    private static final String EXTRA_MOVIE_ID = "com.team7.moviefinder.movie.id";

    public static Intent newIntent(Context packageContext, int id) {
        Intent intent = new Intent(packageContext, SingleMovieView.class);
        intent.putExtra(EXTRA_MOVIE_ID, id);
        return intent;
    }
    @Override
    protected Fragment createFragment() {

        int id = (int) getIntent()
                .getSerializableExtra(EXTRA_MOVIE_ID);
        return SingleMovieFragment.newInstance(id);
        //return null;
    }
}
