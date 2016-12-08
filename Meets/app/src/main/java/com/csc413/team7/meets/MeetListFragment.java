package com.csc413.team7.meets;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 *  This fragment contains Story List and the search logic
 */

public class MeetListFragment extends Fragment {

    private RecyclerView mStoryRecyclerView;
    private StoryAdapter mAdapter;
    private EditText mSearchBar;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story_list, container, false);

        mSearchBar = (EditText) view.findViewById(R.id.search_query);
        mStoryRecyclerView = (RecyclerView) view.findViewById(R.id.story_recycler_view);
        mStoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        mSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(mAdapter != null){
                    mAdapter.setmStories( StoryFactory.getInstance(getContext()).searchStories(charSequence.toString()));
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void updateUI() {
        StoryFactory storyFactory = StoryFactory.getInstance(getActivity());
        List<Story> stories = storyFactory.getStories();

        if (mAdapter == null) {
            mAdapter = new StoryAdapter(stories);
            mStoryRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private class StoryHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mNumber;
        private TextView mDescription;
        private TextView mName;
        private ImageView mImage;

        private Story mStory;

        public StoryHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mNumber = (TextView) itemView.findViewById(R.id.list_story_number_text_view);
            mDescription = (TextView) itemView.findViewById(R.id.list_story_description_text_view);
            mName = (TextView) itemView.findViewById(R.id.list_story_name_text_view);
            mImage = (ImageView) itemView.findViewById(R.id.list_story_image_view);
        }

        public void bindStory(Story story, int position) {
            mStory = story;
            mDescription.setText(mStory.getmDescription());
            mName.setText(mStory.getmName());
            mImage.setImageBitmap(mStory.getImg());
            mNumber.setText("Card " + position);
        }

        @Override
        public void onClick(View view) {
            Intent intent = SingleStoryViewActivity.newIntent(getActivity(), mStory.getmUuid());
            startActivity(intent);
        }
    }

    private class StoryAdapter extends RecyclerView.Adapter<StoryHolder> {
        public List<Story> mStories;

        public StoryAdapter(List<Story> stories) {
            mStories = stories;
        }

        @Override
        public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.story_list_item, parent, false);
            return new StoryHolder(view);
        }

        @Override
        public void onBindViewHolder(StoryHolder holder, int position) {
            Story story = mStories.get(position);
            holder.bindStory(story, position);

        }

        @Override
        public int getItemCount() {
            return mStories.size();
        }

        public void setmStories(List<Story> mStories) {
            this.mStories = mStories;
        }
    }
}
