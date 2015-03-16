package com.codepath.apps.SimpleTweets.fragments;

import com.codepath.apps.SimpleTweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by kelei on 3/14/15.
 */
public class MentionsTimelineFragment extends TweetsListFragment {
    @Override
    protected void populateTimeline(final long maxId) {
        client.getMentionsTimeline(maxId, new JsonHttpResponseHandler()  {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                if (maxId == Long.MAX_VALUE) {
                    aTweets.clear();
                }

                aTweets.addAll(Tweet.fromJSONArray(response));
                aTweets.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                swipeContainer.setRefreshing(false);
            }
        });
    }
}
