package com.codepath.apps.SimpleTweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.codepath.apps.SimpleTweets.R;
import com.codepath.apps.SimpleTweets.TwitterApplication;
import com.codepath.apps.SimpleTweets.adapters.TweetsArrayAdapter;
import com.codepath.apps.SimpleTweets.extensions.EndlessScrollListener;
import com.codepath.apps.SimpleTweets.models.Tweet;
import com.codepath.apps.SimpleTweets.utils.TwitterClient;


import java.util.ArrayList;

/**
 * Created by kelei on 3/14/15.
 */
public class TweetsListFragment extends Fragment {
    protected TwitterClient client;
    protected ArrayList<Tweet> tweets;
    protected TweetsArrayAdapter aTweets;
    protected ListView lvTweets;
    protected SwipeRefreshLayout swipeContainer;
    private TweetsListFragmentListener listener;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);

        client = TwitterApplication.getRestClient();
        tweets = new ArrayList<>();
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
        aTweets.setListener(listener);
        lvTweets.setAdapter(aTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTimeline(getMaxId());
            }
        });

        progressBar = new ProgressBar(getActivity());
        progressBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        lvTweets.setEmptyView(progressBar);

        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeline(Long.MAX_VALUE);
            }
        });
        populateTimeline(Long.MAX_VALUE);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private long getMaxId() {
        long maxId = Long.MAX_VALUE;
        for (int i = 0; i < aTweets.getCount(); i++) {
            if (aTweets.getItem(i).getUid() < maxId) {
                maxId = aTweets.getItem(i).getUid() - 1;
            }
        }
        return maxId;
    }

    protected void populateTimeline(final long maxId) {
        // To be overridden
    }

    public void resetList() {
        tweets.clear();
        populateTimeline(Long.MAX_VALUE);
    }

    public void setListener(TweetsListFragmentListener listener) {
        this.listener = listener;
        if (aTweets != null) {
            aTweets.setListener(listener);
        }
    }

    public interface TweetsListFragmentListener {
        public void onProfileClicked(String screenName);
    }
}
