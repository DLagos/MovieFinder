package com.csc413.team7.meets;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Dion on 11/28/16.
 */

public class SingleMeetViewActivity extends SingleFragmentActivity {

    private static final String EXTRA_STORY_ID = "com.team7.storyfinder.story.uuid";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, SingleStoryViewActivity.class);
        intent.putExtra(EXTRA_STORY_ID, crimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {

        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_STORY_ID);
        return SingleStoryFragment.newInstance(crimeId);
        //return null;
    }
}