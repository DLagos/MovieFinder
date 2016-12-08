package com.csc413.team7.meets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by LIBEXTMAC on 11/24/16.
 */

public class MeetFactory {

    private static MeetFactory mMeetFactory;

    private List<Meet> mStories;
    private int[] picIds = {R.drawable.catguy, R.drawable.mickey, R.drawable.minnie, R.drawable.simba, R.drawable.sfsu_logo};
    private String[] names = {"Cat Guy", "Mickey Mouse", "Minnie Mouse", "Simba", "SFSU"};

    public static StoryFactory getInstance(Context context){

        if(sStoryFactory == null){
            sStoryFactory = new StoryFactory(context);
        }
        return sStoryFactory;
    }

    private StoryFactory(Context context){

        mStories = new ArrayList<Story>();
        Random newRand = new Random();
        for(int i = 0; i<50; i++) {
            int index = newRand.nextInt(5);
            Story story = new Story(UUID.randomUUID(), "Story " + i, "This is a story about " + names[index], BitmapFactory.decodeResource(context.getResources(), picIds[index]));

            mStories.add(story);}
    }

    public List<Story> getStories() {
        return mStories;
    }

    public List<Story> searchStories(String title){
        List<Story> stories = new ArrayList<>();
        for(Story s: mStories){
            if(s.getmName().contains(title)){
                stories.add(s);
            }
        }
        return stories;
    }

    public Story getStory(UUID id) {
        for (Story story : mStories) {
            if (story.getmUuid().equals(id)) {
                return story;
            }
        }
        return null;
    }
}
