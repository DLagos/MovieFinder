package com.team7.storyfinder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;

/**
 * FOR SINGLE STORY VIEW
 */

public class SingleStoryFragment extends Fragment{

    private static final String ARG_STORY_ID = "story_id";
    private Story mStory;
    private TextView mName;
    private TextView mDesc;
    private ImageView mPic;

    public static SingleStoryFragment newInstance(UUID storyId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_STORY_ID, storyId);

        SingleStoryFragment fragment = new SingleStoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID storyId = (UUID) getArguments().getSerializable(ARG_STORY_ID);
        mStory = StoryFactory.getInstance(getActivity()).getStory(storyId);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_single_story, container, false);
        mName = (TextView) v.findViewById(R.id.single_story_name);
        mDesc = (TextView) v.findViewById(R.id.single_story_desc);
        mPic = (ImageView) v.findViewById(R.id.sfsu_logo);
        mName.setText(mStory.getmName());
        mDesc.setText(mStory.getmDescription());
        mPic.setImageBitmap(mStory.getImg());
        return v;
    }
}
