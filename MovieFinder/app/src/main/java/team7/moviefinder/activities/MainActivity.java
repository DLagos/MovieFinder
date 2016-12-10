package team7.moviefinder.activities;

import android.support.v4.app.Fragment;

import team7.moviefinder.fragments.MovieListFragment;
import team7.moviefinder.fragments.SingleFragmentActivity;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

        return new MovieListFragment();
    }

}
