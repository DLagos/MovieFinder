package team7.moviefinder.activities;

import android.support.v4.app.Fragment;

import team7.moviefinder.fragments.SingleFragmentActivity;
import team7.moviefinder.fragments.SingleMovieFragment;

/**
 * Created by Dion on 12/9/16.
 */

public class SingleMovieView extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new SingleMovieFragment();
    }
}
