package com.codepath.apps.SimpleTweets.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.SimpleTweets.R;
import com.codepath.apps.SimpleTweets.TwitterApplication;
import com.codepath.apps.SimpleTweets.fragments.UserTimelineFragment;
import com.codepath.apps.SimpleTweets.models.User;
import com.codepath.apps.SimpleTweets.utils.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ProfileActivity extends ActionBarActivity {
    private TwitterClient client;
    private User user;
    private ImageView ivProfileBackground;
    private ImageView ivProfileImage;
    private TextView tvTweetCount;
    private TextView tvFollowingCount;
    private TextView tvFollowerCount;
    private TextView tvUserName;
    private TextView tvScreenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivProfileBackground = (ImageView) findViewById(R.id.ivProfileBackground);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvTweetCount = (TextView) findViewById(R.id.tvTweetCount);
        tvFollowingCount = (TextView) findViewById(R.id.tvFollowingCount);
        tvFollowerCount = (TextView) findViewById(R.id.tvFollowerCount);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        String screenName = getIntent().getStringExtra("screenName");

        client = TwitterApplication.getRestClient();
        if (screenName == null) {
            client.getCurrentUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);
                    populateUserProfile(user);
                }
            });
        } else {
            client.getUserInfo(screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);
                    populateUserProfile(user);
                }
            });
        }

        if (savedInstanceState == null) {
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, userTimelineFragment);
            ft.commit();
        }

    }

    private void populateUserProfile(User user) {
        getSupportActionBar().setTitle(user.getName());
        Picasso.with(ProfileActivity.this).load(user.getProfileBannerUrl()).into(ivProfileBackground);
        Picasso.with(ProfileActivity.this).load(user.getProfileImageUrl()).into(ivProfileImage);
        tvTweetCount.setText(String.valueOf(user.getStatusesCount()));
        tvFollowingCount.setText(String.valueOf(user.getFollowingCount()));
        tvFollowerCount.setText(String.valueOf(user.getFollowersCount()));
        tvUserName.setText(user.getName());
        tvScreenName.setText(user.getScreenName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
