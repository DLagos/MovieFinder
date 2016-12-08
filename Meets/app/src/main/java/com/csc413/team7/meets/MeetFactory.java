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

    private List<Meet> mMeets;
    //private int[] picIds = {R.drawable.catguy, R.drawable.mickey, R.drawable.minnie, R.drawable.simba, R.drawable.sfsu_logo};
    //private String[] names = {"Cat Guy", "Mickey Mouse", "Minnie Mouse", "Simba", "SFSU"};

    public static MeetFactory getInstance(Context context){

        if(mMeetFactory == null){
            mMeetFactory = new MeetFactory(context);
        }
        return mMeetFactory;
    }

    private MeetFactory(Context context){

        mMeets = new ArrayList<Meet>();
        Random newRand = new Random();
        for(int i = 0; i<50; i++) {
            int index = newRand.nextInt(5);
            Meet meet = new Meet(UUID.randomUUID(), "Story " + i, "This is a story about ", BitmapFactory.decodeResource(context.getResources(), picIds[index]));

            mMeets.add(meet);}
    }

    public List<Meet> getStories() {
        return mMeets;
    }

    public List<Meet> searchStories(String title){
        List<Meet> meets = new ArrayList<>();
        for(Meet s: mMeets){
            if(s.getName().contains(title)){
                meets.add(s);
            }
        }
        return meets;
    }

    public Meet getStory(UUID id) {
        /*
        for (Meet meet : mMeets) {
            if (meet.().equals(id)) {
                return meet;
            }
        }
        */
        return null;

    }
}
