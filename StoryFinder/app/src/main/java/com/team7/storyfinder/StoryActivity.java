package com.team7.storyfinder;


import android.support.v4.app.Fragment;

/**
 * LIST OF STORIES
 */
public class StoryActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

        return new StoryListFragment();

    }

}
