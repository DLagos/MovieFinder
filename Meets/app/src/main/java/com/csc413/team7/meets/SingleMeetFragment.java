package com.csc413.team7.meets;

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

public class SingleMeetFragment extends Fragment{

    private static final String ARG_STORY_ID = "meet_id";
    private Meet mMeet;
    private TextView mName;
    private TextView mDesc;
    private ImageView mPic;

    public static SingleMeetFragment newInstance(UUID storyId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_STORY_ID, storyId);

        SingleMeetFragment fragment = new SingleMeetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID storyId = (UUID) getArguments().getSerializable(ARG_STORY_ID);
        mMeet = MeetFactory.getInstance(getActivity()).getStory(storyId);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_single_meet, container, false);
        mName = (TextView) v.findViewById(R.id.single_meet_title);
        mDesc = (TextView) v.findViewById(R.id.single_meet_desc);
        mName.setText(mMeet.getName());
        mDesc.setText(mMeet.getDescription());
        return v;
    }
}

